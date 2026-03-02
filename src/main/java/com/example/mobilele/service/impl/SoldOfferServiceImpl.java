package com.example.mobilele.service.impl;

import com.example.mobilele.init.OfferSeedContext;
import com.example.mobilele.init.OfferSeedGenerator;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.SoldOfferEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.service.SoldOfferService;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SoldOfferServiceImpl implements SoldOfferService {
  private final SoldOfferRepository soldOfferRepository;
  private final OfferSeedGenerator seedGenerator;

  public SoldOfferServiceImpl(SoldOfferRepository soldOfferRepository, OfferSeedGenerator seedGenerator) {
    this.soldOfferRepository = soldOfferRepository;
    this.seedGenerator = seedGenerator;
  }

  public void initSoldOffers() {
    if (soldOfferRepository.count() > 0) {
      return;
    }

    Random random = new Random();
    long fiveYears = 5L * 365 * 24 * 60 * 60;

    List<SoldOfferEntity> soldOffers = new ArrayList<>();

    for (OfferSeedContext data : seedGenerator.generateData()) {

      Instant saleTime = Instant.now()
              .minusSeconds(random.nextLong(fiveYears));

      SoldOfferEntity sold = buildSoldOffer(
              data.model(),
              data.engine(),
              data.transmission(),
              data.condition(),
              data.color(),
              data.mileage() + 5000,
              data.price().subtract(BigDecimal.valueOf(1500)),
              data.description(),
              data.seller(),
              data.country(),
              data.city(),
              50 + random.nextInt(500),
              saleTime
      );
      soldOffers.add(sold);
    }
    soldOfferRepository.saveAll(soldOffers);
  }

  private SoldOfferEntity buildSoldOffer(
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
          long views,
          Instant saleTime
  ) {
    SoldOfferEntity offer = new SoldOfferEntity();

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
    offer.setViews(views);
    offer.setSaleTime(saleTime);

    return offer;
  }
}
