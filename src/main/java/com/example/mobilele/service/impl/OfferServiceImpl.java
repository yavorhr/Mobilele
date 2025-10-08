package com.example.mobilele.service.impl;

import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

  public OfferServiceImpl(OfferRepository offerRepository, ModelService modelService, ModelMapper modelMapper, UserService userService, BrandService brandService) {
    this.offerRepository = offerRepository;
    this.modelService = modelService;
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.brandService = brandService;
  }

  @Override
  public List<OfferViewModel> findAllOffers() {
    return this.offerRepository
            .findAll()
            .stream().map(o -> this.modelMapper.map(o, OfferViewModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public OfferServiceModel findOfferById(String name, Long id) {
    OfferEntity offer = this.offerRepository.findById(id).get();

    OfferServiceModel model = this.modelMapper.map(offer, OfferServiceModel.class);
    model.setSellerFullName(String.format("%s %s", offer.getSeller().getFirstName(), offer.getSeller().getLastName()));
    model.setCanModify(isOwner(name, offer.getId()));

    return model;
  }

  @Override
  public void deleteById(Long id) {
    this.offerRepository.deleteById(id);
  }

  @Override
  public OfferAddServiceModel addOffer(OfferAddServiceModel offerServiceModel, String username) {
    OfferEntity offer = this.modelMapper.map(offerServiceModel, OfferEntity.class);

    ModelEntity modelEntity = this.modelService.findByName(offerServiceModel.getModel()).get();
    modelEntity.setBrand(this.brandService.findBrandByName(offerServiceModel.getBrand()).get());

    offer.setModel(modelEntity);
    offer.setSeller(this.userService.findByUsername(username));

    offer = this.offerRepository.save(offer);
    offerServiceModel.setId(offer.getId());

    return offerServiceModel;
  }

  @Override
  public Collection<OfferServiceModel> findOffersByBrand(String brand) {
    return this.offerRepository.findAllByModel_BrandName(brand)
            .stream()
            .map(offerEntity ->
                    this.modelMapper.map(offerEntity, OfferServiceModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public void updateOffer(OfferUpdateServiceModel serviceModel, Long id) {

    OfferEntity offerEntity =
            offerRepository.findById(serviceModel.getId()).orElseThrow(() ->
                    new ObjectNotFoundException("Offer with id " + serviceModel.getId() + " not found!"));

    this.modelMapper.map(serviceModel, offerEntity);

    this.offerRepository.save(offerEntity);
  }

  public boolean isOwner(String username, Long id) {
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

  private boolean isAdmin(UserEntity user) {
    return user.
            getRoles().
            stream().
            map(UserRoleEntity::getRole).
            anyMatch(r -> r == UserRoleEnum.ADMIN);
  }

  @Override
  public void initOffers() {
    if (offerRepository.count() == 0) {
      OfferEntity firstOffer = new OfferEntity();

      firstOffer
              .setModel(this.modelService.findById(1L)
                      .orElseThrow(() -> new ObjectNotFoundException("Model with id: 1L does not exist!")))
              .setEngine(EngineEnum.Gasoline)
              .setTransmission(TransmissionType.Automatic)
              .setCondition(ConditionEnum.USED)
              .setVehicleCategory(VehicleCategoryEnum.CAR)
              .setColor(ColorEnum.GRAY)
              .setMileage(22500.30)
              .setPrice(14300.00)
              .setYear(2010)
              .setDescription("Used, but well services and in good condition.")
              .setImageUrl("/images/cars/m1")
              .setSeller(userService.findByUsername("admin"));

      OfferEntity secondOffer = new OfferEntity();

      secondOffer
              .setModel(this.modelService.findById(2L)
                      .orElseThrow(() -> new ObjectNotFoundException("Model with id: 2L does not exist!")))
              .setEngine(EngineEnum.Gasoline)
              .setTransmission(TransmissionType.Manual)
              .setCondition(ConditionEnum.NEW)
              .setVehicleCategory(VehicleCategoryEnum.SUV)
              .setColor(ColorEnum.WHITE)
              .setMileage(500.40)
              .setPrice(60000.00)
              .setYear(2005)
              .setDescription("The SUV is brand new, just get in and drive!")
              .setImageUrl("/images/cars/x3")
              .setSeller(userService.findByUsername("user"));

      OfferEntity thirdOffer = new OfferEntity();

      thirdOffer
              .setModel(this.modelService.findById(3L)
                      .orElseThrow(() -> new ObjectNotFoundException("Model with id: 3L does not exist!")))
              .setEngine(EngineEnum.Gasoline)
              .setTransmission(TransmissionType.Manual)
              .setVehicleCategory(VehicleCategoryEnum.SUV)
              .setCondition(ConditionEnum.DAMAGED)
              .setColor(ColorEnum.BLUE)
              .setMileage(10000.40)
              .setPrice(3000.00)
              .setYear(2011)
              .setDescription("The car is a bit damaged in the back, but this can be fixed easily!")
              .setImageUrl("/images/cars/q5")
              .setSeller(userService.findByUsername("user"));

      OfferEntity fourthOffer = new OfferEntity();

      fourthOffer
              .setModel(this.modelService.findById(4L)
                      .orElseThrow(() -> new ObjectNotFoundException("Model with id: 4L does not exist!")))
              .setEngine(EngineEnum.Hybrid)
              .setTransmission(TransmissionType.Automatic)
              .setVehicleCategory(VehicleCategoryEnum.SUV)
              .setCondition(ConditionEnum.FOR_PARTS)
              .setColor(ColorEnum.WHITE)
              .setMileage(1000000.40)
              .setPrice(1000.00)
              .setYear(2022)
              .setDescription("The car is totally damaged and it could be used for spare parts!")
              .setImageUrl("/images/cars/rav4")
              .setSeller(userService.findByUsername("admin"));

      offerRepository.saveAll(List.of(firstOffer, secondOffer, thirdOffer, fourthOffer));
    }
  }
}
