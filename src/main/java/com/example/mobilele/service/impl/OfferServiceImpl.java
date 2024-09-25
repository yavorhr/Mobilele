package com.example.mobilele.service.impl;

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
                      "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Bill_Gates_-_2023_-_P062021-967902_%28cropped%29.jpg/798px-Bill_Gates_-_2023_-_P062021-967902_%28cropped%29.jpg");

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
              .setImageUrl("https://upload.wikimedia.org/wikipedia/commons/0/03/Jeff_Bezos_visits_LAAFB_SMC_%283908618%29_%28cropped%29.jpeg");

      offerRepository.saveAll(List.of(offerEntity1, offerEntity2));
    }
  }
}
