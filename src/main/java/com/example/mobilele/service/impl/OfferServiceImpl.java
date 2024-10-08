package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.view.OfferViewModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
  private final OfferRepository offerRepository;
  private final ModelService modelService;
  private final ModelMapper modelMapper;
  private final UserService userService;

  public OfferServiceImpl(OfferRepository offerRepository, ModelService modelService, ModelMapper modelMapper, UserService userService) {
    this.offerRepository = offerRepository;
    this.modelService = modelService;
    this.modelMapper = modelMapper;
    this.userService = userService;
  }

  @Override
  public void initOffers() {
    if (offerRepository.count() == 0) {
      OfferEntity offerEntity1 = new OfferEntity();

      offerEntity1
              .setModel(this.modelService.findById(1L).orElse(null))
              .setEngine(EngineEnum.GASOLINE)
              .setTransmission(TransmissionType.MANUAL)
              .setMileage(22500.30)
              .setPrice(14300.00)
              .setYear(2022)
              .setDescription("Used, but well services and in good condition.")
              .setSeller(userService.findByUsername("pesho")
                      .orElse(null)) // or currentUser.getUserName()
              .setImageUrl(
                      "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bc/BMW_U11_1X7A6826.jpg/420px-BMW_U11_1X7A6826.jpg");

      OfferEntity offerEntity2 = new OfferEntity();

      offerEntity2
              .setModel(this.modelService.findById(2L).orElse(null))
              .setEngine(EngineEnum.GASOLINE)
              .setTransmission(TransmissionType.MANUAL)
              .setMileage(500.40)
              .setPrice(60000.00)
              .setYear(2020)
              .setDescription("Perfect condition!.")
              .setSeller(userService.findByUsername("admin")
                      .orElse(null)) // or currentUser.getUserName()
              .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5d/2018_BMW_X3_%28G01%29_xDrive30i_wagon_%282018-11-02%29_01.jpg/1920px-2018_BMW_X3_%28G01%29_xDrive30i_wagon_%282018-11-02%29_01.jpg");

      offerRepository.saveAll(List.of(offerEntity1, offerEntity2));
    }
  }

  @Override
  public List<OfferViewModel> findAllOffers() {
    return this.offerRepository
            .findAll()
            .stream().map(o -> this.modelMapper.map(o, OfferViewModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public OfferViewModel findOfferById(Long id) {
    OfferEntity offer = this.offerRepository.findById(id).get();

    OfferViewModel model = this.modelMapper.map(offer, OfferViewModel.class);
    model.setSellerFullName(String.format("%s %s", offer.getSeller().getFirstName(), offer.getSeller().getLastName()));
    return model;
  }

  @Override
  public void deleteById(Long id) {
    this.offerRepository.deleteById(id);
  }
}
