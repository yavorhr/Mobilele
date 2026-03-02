package com.example.mobilele.init;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.enums.*;
import java.math.BigDecimal;

public record OfferSeedContext(
        ModelEntity model,
        EngineEnum engine,
        TransmissionType transmission,
        ConditionEnum condition,
        ColorEnum color,
        CityEnum city,
        CountryEnum country,
        UserEntity seller,
        double mileage,
        BigDecimal price,
        String description,
        int index) {
  }

