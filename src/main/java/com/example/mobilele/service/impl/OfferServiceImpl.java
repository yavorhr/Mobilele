package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.dto.service.offer.OfferServiceModel;
import com.example.mobilele.model.dto.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.dto.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
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
  public OfferServiceModel findOfferById(Long id) {
    OfferEntity offer = this.offerRepository.findById(id).get();

    OfferServiceModel model = this.modelMapper.map(offer, OfferServiceModel.class);
    model.setSellerFullName(String.format("%s %s", offer.getSeller().getFirstName(), offer.getSeller().getLastName()));

    return model;
  }

  @Override
  public void deleteById(Long id) {
    this.offerRepository.deleteById(id);
  }

  @Override
  public OfferAddServiceModel addOffer(OfferAddServiceModel offerServiceModel, Long id) {
    OfferEntity offer = this.modelMapper.map(offerServiceModel, OfferEntity.class);

    ModelEntity modelEntity = this.modelService.findByName(offerServiceModel.getModel());
    modelEntity.setBrand(this.brandService.findBrandByName(offerServiceModel.getBrand()).get());

    offer.setModel(modelEntity);
    offer.setSeller(this.userService.findById(id));

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

  @Override
  public void initOffers() {
    if (offerRepository.count() == 0) {
      OfferEntity firstOffer = new OfferEntity();

      firstOffer
              .setModel(this.modelService.findById(1L).orElse(null))
              .setEngine(EngineEnum.Gasoline)
              .setTransmission(TransmissionType.Manual)
              .setMileage(22500.30)
              .setPrice(14300.00)
              .setYear(2000)
              .setDescription("Used, but well services and in good condition.")
              .setSeller(userService.findByUsername("pesho")
                      .orElse(null)) // or currentUser.getUserName()
              .setImageUrl(
                      "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/BMW_U11_1X7A6826.jpg/420px-BMW_U11_1X7A6826.jpg");

      OfferEntity secondOffer = new OfferEntity();

      secondOffer
              .setModel(this.modelService.findById(2L).orElse(null))
              .setEngine(EngineEnum.Gasoline)
              .setTransmission(TransmissionType.Manual)
              .setMileage(500.40)
              .setPrice(60000.00)
              .setYear(2005)
              .setDescription("Perfect condition!.")
              .setSeller(userService.findByUsername("admin")
                      .orElse(null)) // or currentUser.getUserName()
              .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/2018_BMW_X3_%28G01%29_xDrive30i_wagon_%282018-11-02%29_01.jpg/1920px-2018_BMW_X3_%28G01%29_xDrive30i_wagon_%282018-11-02%29_01.jpg");

      offerRepository.saveAll(List.of(firstOffer, secondOffer));
    }
  }
}
