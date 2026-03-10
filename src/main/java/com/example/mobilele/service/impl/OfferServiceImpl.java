package com.example.mobilele.service.impl;

import com.example.mobilele.init.OfferSeedContext;
import com.example.mobilele.init.OfferSeedGenerator;
import com.example.mobilele.model.binding.offer.OfferUpdateBindingForm;
import com.example.mobilele.model.entity.*;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.*;
import com.example.mobilele.util.ProjectHelpers;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OfferServiceImpl implements OfferService {
  public static final String CLOUD_NAME = "yavorhr";
  public static final String ROOT_FOLDER = "mobilele";
  public static final String BASE_URL =
          "https://res.cloudinary.com/" + CLOUD_NAME + "/image/upload/" + ROOT_FOLDER + "/";
  private static final int SELLERS_PAGE_SIZE = 5;


  private final OfferRepository offerRepository;
  private final UserRepository userRepository;
  private final SoldOfferRepository soldOfferRepository;
  private final ModelService modelService;
  private final ModelMapper modelMapper;
  private final UserService userService;
  private final BrandService brandService;
  private final CloudinaryService cloudinaryService;
  private final OfferSeedGenerator seedGenerator;

  public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, SoldOfferRepository soldOfferRepository, ModelService modelService, ModelMapper modelMapper, UserService userService, BrandService brandService, CloudinaryService cloudinaryService, OfferSeedGenerator seedGenerator) {
    this.offerRepository = offerRepository;
    this.userRepository = userRepository;
    this.soldOfferRepository = soldOfferRepository;
    this.modelService = modelService;
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.brandService = brandService;
    this.cloudinaryService = cloudinaryService;
    this.seedGenerator = seedGenerator;
  }

  @Override
  public Page<OfferBaseViewModel> findAllOffers(Pageable pageable) {
    return this.offerRepository
            .findAll(pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Override
  public OfferViewModel findOfferById(String name, Long id) {
    OfferEntity offer =
            this.offerRepository
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Offer with ID: " + id + " does not exist!"));

    OfferViewModel model = this.modelMapper.map(offer, OfferViewModel.class);

    model.setCanModify(userService.isOwnerOrIsAdmin(name, offer.getId()));
    model.setNotOwnerOrIsAdmin(userService.isNotOwnerOrIsAdmin(name, offer.getId()));
    model.setReserved(offer.isReserved());

    return model;
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    OfferEntity offer = this.offerRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " was not found!"));

    userRepository.deleteFavoritesByOfferId(id);

    for (Picture picture : offer.getPictures()) {
      try {
        cloudinaryService.delete(picture.getPublicId());
      } catch (Exception e) {
        log.warn("Cloudinary deletion failed for {}", picture.getPublicId(), e);
      }
    }

    this.offerRepository.delete(offer);
  }

  @Override
  public OfferAddServiceModel addOffer(OfferAddServiceModel offerServiceModel, String username) throws IOException {
    OfferEntity offer = this.modelMapper.map(offerServiceModel, OfferEntity.class);

    //Set brand and model
    ModelEntity modelEntity = this.modelService.findByName(offerServiceModel.getModel());
    modelEntity.setBrand(this.brandService.findBrandByName(offerServiceModel.getBrand()));

    offer.setModel(modelEntity);

    // Set seller
    UserEntity seller = this.userService.findByUsername(username);
    offer.setSeller(seller);

    //Set pictures
    offer.getPictures().clear();
    for (MultipartFile file : offerServiceModel.getPictures()) {
      CloudinaryImage uploaded = cloudinaryService.upload(file, "api");

      Picture picture = new Picture();

      picture.setUrl(uploaded.getUrl());
      picture.setPublicId(uploaded.getPublicId());

      picture.setOffer(offer);
      picture.setSeller(seller);
      picture.setTitle(ProjectHelpers.convertPictureTitle(file.getOriginalFilename()));

      offer.getPictures().add(picture);
    }

    offer = this.offerRepository.save(offer);

    offerServiceModel.setId(offer.getId());

    return offerServiceModel;
  }

  @Override
  public Collection<OfferViewModel> findOffersByBrandAndVehicleType(String brand, VehicleCategoryEnum vehicleType) {
    return this.offerRepository.findAllByModel_Brand_NameAndModel_VehicleType(brand, vehicleType)
            .stream()
            .map(offerEntity ->
                    this.modelMapper.map(offerEntity, OfferViewModel.class))
            .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public void updateOffer(OfferUpdateServiceModel serviceModel, Long id) {

    OfferEntity offerEntity =
            offerRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " not found!"));

    this.modelMapper.map(serviceModel, offerEntity);

    this.offerRepository.save(offerEntity);
  }

  public Page<OfferBaseViewModel> findOffersByFilters(
          OffersFindServiceModel filter,
          VehicleCategoryEnum vehicleType,
          Pageable pageable) {

    Specification<OfferEntity> spec = (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Mandatory filters
      predicates.add(cb.equal(root.get("model").get("brand").get("name"), filter.getBrand()));

      if (filter.getModel() != null && !filter.getModel().isBlank()) {
        predicates.add(cb.equal(root.get("model").get("name"), filter.getModel()));
      }
      predicates.add(cb.equal(root.get("model").get("vehicleType"), vehicleType));

      // Optional filters
      if (filter.getPrice() != null) {

        Expression<BigDecimal> pricePath = root.get("price").as(BigDecimal.class);

        switch (filter.getPriceComparison()) {
          case "under":
            predicates.add(cb.lessThanOrEqualTo(pricePath, filter.getPrice()));
            break;
          case "above":
            predicates.add(cb.greaterThanOrEqualTo(pricePath, filter.getPrice()));
            break;
          case "between":
            if (filter.getPriceMax() != null) {
              predicates.add(cb.between(pricePath, filter.getPrice(), filter.getPriceMax()));
            }
            break;
        }
      }

      if (filter.getMileage() != null) {
        switch (filter.getMileageComparison()) {
          case "under":
            predicates.add(cb.lessThanOrEqualTo(root.get("mileage"), filter.getMileage()));
            break;
          case "above":
            predicates.add(cb.greaterThanOrEqualTo(root.get("mileage"), filter.getMileage()));
            break;
          case "between":
            if (filter.getMileageMax() != null) {
              predicates.add(cb.between(root.get("mileage"), filter.getMileage(), filter.getMileageMax()));
            }
            break;
        }
      }

      if (filter.getYear() != null) {
        switch (filter.getYearComparison()) {
          case "under":
            predicates.add(cb.lessThanOrEqualTo(root.get("model").get("year"), filter.getYear()));
            break;
          case "above":
            predicates.add(cb.greaterThanOrEqualTo(root.get("model").get("year"), filter.getYear()));
            break;
          case "between":
            if (filter.getYearMax() != null) {
              predicates.add(cb.between(root.get("model").get("year"), filter.getYear(), filter.getYearMax()));
            }
            break;
        }
      }

      if (filter.getEngine() != null) predicates.add(cb.equal(root.get("engine"), filter.getEngine()));
      if (filter.getTransmission() != null)
        predicates.add(cb.equal(root.get("transmission"), filter.getTransmission()));
      if (filter.getCondition() != null) predicates.add(cb.equal(root.get("condition"), filter.getCondition()));
      if (filter.getColor() != null) predicates.add(cb.equal(root.get("color"), filter.getColor()));
      if (filter.getCountry() != null) predicates.add(cb.equal(root.get("country"), filter.getCountry()));
      if (filter.getCity() != null) predicates.add(cb.equal(root.get("city"), filter.getCity()));

      return cb.and(predicates.toArray(new Predicate[0]));
    };

    return offerRepository
            .findAll(spec, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Override
  public OfferEntity findById(long id) {
    return this.offerRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Offer with id" + id + " was not found!"));
  }

  @Override
  public Page<OfferBaseViewModel> findOffersByBrand(String brandName, Pageable pageable) {

    return offerRepository
            .findAllByModel_Brand_Name(brandName, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Override
  public Page<OfferBaseViewModel> findOffersByUserId(String username, Pageable pageable) {
    return offerRepository
            .findAllBySeller_Username(username, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Override
  public List<OfferBaseViewModel> findLatestOffers(int count) {
    return offerRepository.findAllByOrderByCreatedDesc(PageRequest.of(0, count))
            .stream()
            .map(this::mapToOfferBaseViewModel)
            .toList();
  }

  @Override
  public List<OfferBaseViewModel> findMostViewedOffers(int count) {
    return offerRepository.findAllByOrderByViewsDesc(PageRequest.of(0, count))
            .stream()
            .map(this::mapToOfferBaseViewModel)
            .toList();
  }

  @Override
  @Transactional
  public boolean doesOfferExistInUsersFavorites(Long id, String username) {
    UserEntity user = this.userService.findByUsername(username);

    OfferEntity offer = this.offerRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Offer with id: " + id + " does not exist!"));

    return user.getFavorites()
            .stream()
            .anyMatch(o -> o.getId().equals(offer.getId()));
  }

  @Override
  public Page<OfferBaseViewModel> findFavoriteOffers(String username, Pageable pageable) {
    return offerRepository
            .findFavoritesByUsername(username, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Transactional
  @Override
  public boolean toggleReservation(Long id, String username) {
    OfferEntity offer = offerRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Offer not found"));

    offer.setReserved(!offer.isReserved());
    offerRepository.save(offer);

    return offer.isReserved();
  }

  @Transactional
  @Override
  public void incrementViews(Long offerId) {
    OfferEntity offer = offerRepository.findById(offerId)
            .orElseThrow(() -> new ObjectNotFoundException("Offer not found"));

    offerRepository.incrementViews(offerId);
  }

  @Override
  public List<OfferBaseViewModel> findTopOffersByViews() {
    return offerRepository.findTop20ByOrderByViewsDesc()
            .stream()
            .map(this::mapToOfferBaseViewModel)
            .toList();
  }

  @Override
  public void saveSoldOffer(Long id) {
    var offerEntity =
            this.offerRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ObjectNotFoundException("Offer with id " + id + "was not found!"));

    var soldOffer = this.modelMapper.map(offerEntity, SoldOfferEntity.class);
    soldOffer.setSaleTime(Instant.now());

    this.soldOfferRepository.save(soldOffer);
  }

  @Override
  public long getSoldVehiclesCount() {
    return soldOfferRepository.count();
  }

  @Override
  public List<TopSellerViewModel> getTop20Sellers() {
    return soldOfferRepository
            .findTop20Sellers(PageRequest.of(0, 20));
  }

  @Override
  public Page<TopSellerViewModel> getSellerPerformanceByYear(int year, int page) {

    ZoneId zone = ZoneId.systemDefault();

    Instant start = LocalDate.of(year, 1, 1)
            .atStartOfDay(zone)
            .toInstant();

    Instant end = LocalDate.of(year + 1, 1, 1)
            .atStartOfDay(zone)
            .toInstant();

    Pageable pageable = PageRequest.of(page, SELLERS_PAGE_SIZE);

    return soldOfferRepository.findSellerPerformanceByPeriod(start, end, pageable);
  }

  @Override
  public Page<SoldOfferViewModel> getSoldCarsByYear(int year, int page) {
    ZoneId zone = ZoneId.systemDefault();

    Instant start = LocalDate.of(year, 1, 1)
            .atStartOfDay(zone)
            .toInstant();

    Instant end = LocalDate.of(year + 1, 1, 1)
            .atStartOfDay(zone)
            .toInstant();

    Pageable pageable = PageRequest.of(page, 10);

    return soldOfferRepository.findSoldCarsByPeriod(start, end, pageable);
  }

  @Override
  public OfferUpdateBindingForm getUpdateForm(Long id) {
    return modelMapper.map(
            offerRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Offer with id :" + id + "does not exist!")),
            OfferUpdateBindingForm.class
    );
  }

  @Override
  public Page<OfferBaseViewModel> findByTypeBrandAndModel(
          VehicleCategoryEnum vehicleCategory,
          String brand,
          String modelName,
          Pageable pageable) {

    return this.offerRepository
            .findAllByModel_VehicleTypeAndModel_Brand_NameAndModel_Name(vehicleCategory, brand, modelName, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  // Init offers
  @Transactional
  @Override
  public void initOffers() {
    if (offerRepository.count() > 0) return;

    List<OfferEntity> offers = new ArrayList<>();

    for (OfferSeedContext data : seedGenerator.generateData()) {

      // Create picture
      String publicId = buildPublicId(data.model());
      String url = buildImageUrl(data.model());

      Picture picture = createPicture(
              data.model().getName(),
              publicId,
              url,
              data.seller()
      );

      OfferEntity offer = buildOffer(
              data.model(),
              data.engine(),
              data.transmission(),
              data.condition(),
              data.color(),
              data.mileage(),
              data.price(),
              data.description(),
              data.seller(),
              data.country(),
              data.city(),
              picture
      );

      offers.add(offer);
    }

    offerRepository.saveAll(offers);
  }

  // Private helper methods
  private OfferEntity buildOffer(
          ModelEntity model,
          EngineEnum engineType,
          TransmissionType transmissionType,
          ConditionEnum condition,
          ColorEnum color,
          Double mileage,
          BigDecimal price,
          String description,
          UserEntity seller,
          CountryEnum country,
          CityEnum city,
          Picture picture) {
    OfferEntity offer = new OfferEntity();

    offer.setModel(model);
    offer.setEngine(engineType);
    offer.setTransmission(transmissionType);
    offer.setCondition(condition);
    offer.setColor(color);
    offer.setMileage(mileage);
    offer.setPrice(price);
    offer.setDescription(description);
    offer.setCountry(country);
    offer.setCity(city);
    offer.setSeller(seller);

    picture.setOffer(offer);
    offer.setPictures(List.of(picture));

    return offer;
  }

  private Picture createPicture(
          String title,
          String publicId,
          String url,
          UserEntity seller
  ) {
    Picture picture = new Picture();
    picture.setTitle(title);
    picture.setPublicId(publicId);
    picture.setUrl(url);
    picture.setSeller(seller);
    return picture;
  }

  private String normalizeForFileName(String value) {
    return value.toUpperCase();
  }

  private String buildPublicId(ModelEntity model) {
    return "cars-offers/"
            + model.getBrand().getName()
            + "/"
            + normalizeForFileName(model.getName());
  }

  private String buildImageUrl(ModelEntity model) {
    return BASE_URL + buildPublicId(model) + ".jpg";
  }

  private OfferBaseViewModel mapToOfferBaseViewModel(OfferEntity e) {
    OfferBaseViewModel viewModel = this.modelMapper.map(e, OfferBaseViewModel.class);
    viewModel.setProfileImage(
            e.getPictures()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException("No pictures found for model " + e.getModel().getName() + " with ID: " + e.getId()))
                    .getUrl());

    return viewModel;
  }
}