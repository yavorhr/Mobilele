package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.*;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.*;
import com.example.mobilele.util.ProjectHelpers;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
  private final OfferRepository offerRepository;
  private final ModelService modelService;
  private final ModelMapper modelMapper;
  private final UserService userService;
  private final BrandService brandService;
  private final CloudinaryService cloudinaryService;

  public OfferServiceImpl(OfferRepository offerRepository, ModelService modelService, ModelMapper modelMapper, UserService userService, BrandService brandService, CloudinaryService cloudinaryService) {
    this.offerRepository = offerRepository;
    this.modelService = modelService;
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.brandService = brandService;
    this.cloudinaryService = cloudinaryService;
  }

  @Override
  public List<OfferViewModel> findAllOffers() {
    return this.offerRepository
            .findAll()
            .stream().map(o -> this.modelMapper.map(o, OfferViewModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public OfferViewModel findOfferById(String name, Long id) {
    OfferEntity offer =
            this.offerRepository
                    .findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Offer with ID: " + id + " does not exist!"));

    OfferViewModel model = this.modelMapper.map(offer, OfferViewModel.class);
    model.setCanModify(isOwnerOrIsAdmin(name, offer.getId()));

    return model;
  }

  @Override
  public void deleteById(Long id) {
    this.offerRepository.deleteById(id);
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
      CloudinaryImage uploaded = cloudinaryService.upload(file, "cars-offers");

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

  @Override
  public void updateOffer(OfferUpdateServiceModel serviceModel) {

    OfferEntity offerEntity =
            offerRepository.findById(serviceModel.
                    getId()).orElseThrow(() ->
                    new ObjectNotFoundException("Offer with id " + serviceModel.getId() + " not found!"));

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
      if (filter.getCondition() != null) predicates.add(cb.equal(root.get("vehicleCondition"), filter.getCondition()));
      if (filter.getColor() != null) predicates.add(cb.equal(root.get("color"), filter.getColor()));
      if (filter.getCountry() != null) predicates.add(cb.equal(root.get("country"), filter.getCountry()));
      if (filter.getCity() != null) predicates.add(cb.equal(root.get("city"), filter.getCity()));

      return cb.and(predicates.toArray(new Predicate[0]));
    };

    return offerRepository
            .findAll(spec, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  public boolean isOwnerOrIsAdmin(String username, Long id) {
    Optional<OfferEntity> offerOpt = offerRepository.
            findById(id);

    UserEntity caller = this.userService.
            findByUsername(username);

    if (offerOpt.isEmpty()) {
      return false;
    } else {
      OfferEntity offerEntity = offerOpt.get();

      return isAdmin(caller) ||
              offerEntity.getSeller().getUsername().equals(username);
    }
  }

  @Override
  public OfferEntity findById(long id) {
    return this.offerRepository
            .findById(id)
            .orElseThrow(()
                    -> new ObjectNotFoundException("Offer with id" + id + " was not found!"));
  }

  @Override
  public List<OfferBaseViewModel> findOffersByBrand(String brandName) {
    return this.offerRepository.findAllByModel_Brand_Name(brandName)
            .stream()
            .map(this::mapToOfferBaseViewModel)
            .collect(Collectors.toList());
  }

  @Override
  public List<OfferBaseViewModel> findOffersByUserId(Long userId) {
    return this.offerRepository.findAllBySeller_Id(userId)
            .stream()
            .map(this::mapToOfferBaseViewModel)
            .collect(Collectors.toList());
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

  @Override
  @Transactional
  public void initOffers() {
    if (offerRepository.count() == 0) {

      // Offer 1
      OfferEntity offer1 = buildOffer(
              1L, EngineEnum.Gasoline, TransmissionType.AUTOMATIC, ConditionEnum.USED,
              ColorEnum.GRAY, 22500.30, BigDecimal.valueOf(14300),
              "Used, but well serviced and in good condition.",
              "admin", CountryEnum.SPAIN, CityEnum.BARCELONA, createPicture("m1", "cars-offers/m1_eicofs",
                      "https://res.cloudinary.com/yavorhr/image/upload/v1759923263/mobilele/cars-offers/m1_eicofs.webp"));
      // Offer 2
      OfferEntity offer2 = buildOffer(
              2L, EngineEnum.Gasoline, TransmissionType.MANUAL, ConditionEnum.NEW
              , ColorEnum.WHITE, 500.00, BigDecimal.valueOf(6500),
              "The SUV is brand new, just get in and drive!",
              "admin", CountryEnum.GERMANY, CityEnum.BERLIN, createPicture("x3", "cars-offers/x3_wxw7fr",
                      "https://res.cloudinary.com/yavorhr/image/upload/v1759923267/mobilele/cars-offers/x3_wxw7fr.jpg"));

      // Offer 3
      OfferEntity offer3 = buildOffer(
              3L, EngineEnum.Gasoline, TransmissionType.MANUAL, ConditionEnum.DAMAGED
              , ColorEnum.BLUE, 10000.40, BigDecimal.valueOf(31000),
              "The SUV is a bit damaged in the back, but this can be fixed easily!",
              "user", CountryEnum.BULGARIA, CityEnum.VARNA, createPicture("rav4", "cars-offers/rav4_j72ktc",
                      "https://res.cloudinary.com/yavorhr/image/upload/v1759923264/mobilele/cars-offers/rav4_j72ktc.jpg"));

      // Offer 4
      OfferEntity offer4 = buildOffer(
              4L, EngineEnum.Hybrid, TransmissionType.AUTOMATIC, ConditionEnum.FOR_PARTS
              , ColorEnum.GREEN, 99999.00, BigDecimal.valueOf(1000),
              "The car is totally damaged and it could be used for spare parts!",
              "user", CountryEnum.BULGARIA, CityEnum.SOFIA, createPicture("q5", "cars-offers/q5_bd67cg",
                      "https://res.cloudinary.com/yavorhr/image/upload/v1759923265/mobilele/cars-offers/q5_bd67cg.jpg"));

      // Save all offers with pictures (cascade persists pictures too)
      offerRepository.saveAll(List.of(offer1, offer2, offer3, offer4));
    }
  }

  // Private and helpers
  private OfferEntity buildOffer(Long modelId, EngineEnum engineType, TransmissionType transmissionType,
                                 ConditionEnum condition, ColorEnum color,
                                 Double mileage, BigDecimal price, String description,
                                 String username, CountryEnum country, CityEnum city, Picture picture) {

    UserEntity seller = userService.findByUsername(username);

    OfferEntity offer = new OfferEntity();
    offer.setModel(modelService.findById(modelId)
            .orElseThrow(() -> new ObjectNotFoundException("Model with id: " + modelId + " does not exist!")));

    offer.setEngine(engineType);
    offer.setTransmission(transmissionType);
    offer.setCondition(condition);
    offer.setColor(color);
    offer.setMileage(mileage);
    offer.setPrice(price);
    offer.setDescription(description);
    offer.setCountry(country);
    offer.setCity(city);

    // Set seller
    offer.setSeller(seller);

    // Set picture and bidirectional relationship
    picture.setOffer(offer);
    picture.setSeller(seller);
    offer.setPictures(List.of(picture));

    return offer;
  }

  private Picture createPicture(String title, String publicId, String url) {
    Picture picture = new Picture();
    picture.setTitle(title);
    picture.setPublicId(publicId);
    picture.setUrl(url);
    return picture;
  }

  private boolean isAdmin(UserEntity user) {
    return user.
            getRoles().
            stream().
            map(UserRoleEntity::getRole).
            anyMatch(r -> r == UserRoleEnum.ADMIN);
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