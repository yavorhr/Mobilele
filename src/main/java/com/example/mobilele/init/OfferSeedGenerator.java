package com.example.mobilele.init;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.*;

@Component
public class OfferSeedGenerator {
  private final ModelService modelService;
  private final UserService userService;

  public OfferSeedGenerator(ModelService modelService, UserService userService) {
    this.modelService = modelService;
    this.userService = userService;
  }

  public List<OfferSeedContext> generateData() {
    List<ModelEntity> models = modelService.findAll();

    UserEntity admin = userService.findByUsername("admin");
    UserEntity user = userService.findByUsername("user");

    List<UserEntity> otherUsers = userService.findAll().stream()
            .filter(u -> !u.getUsername().equals("admin"))
            .filter(u -> !u.getUsername().equals("user"))
            .toList();

    Random random = new Random();
    List<OfferSeedContext> contexts = new ArrayList<>();

    int index = 0;

    for (ModelEntity model : models) {

      EngineEnum engine = EngineEnum.values()[index % EngineEnum.values().length];
      TransmissionType transmission = TransmissionType.values()[index % TransmissionType.values().length];
      ConditionEnum condition = ConditionEnum.values()[index % ConditionEnum.values().length];
      ColorEnum color = ColorEnum.values()[index % ColorEnum.values().length];
      CityEnum city = CityEnum.values()[index % CityEnum.values().length];
      CountryEnum country = city.getCountry();

      UserEntity seller = resolveSeller(index, admin, user, otherUsers, random);

      double mileage = condition == ConditionEnum.New ? 0 : 5_000 + (index * 700);
      BigDecimal price = BigDecimal.valueOf(8_000 + (index * 1_500));

      contexts.add(new OfferSeedContext(
              model, engine, transmission, condition, color,
              city, country, seller, mileage, price,
              buildDescription(model, condition), index
      ));
      index++;
    }

    return contexts;
  }

  private UserEntity resolveSeller(int index,
                                   UserEntity admin,
                                   UserEntity user,
                                   List<UserEntity> otherUsers,
                                   Random random) {
    if (index < 2) return admin;
    if (index < 4) return user;
    return otherUsers.get(random.nextInt(otherUsers.size()));
  }

  private String buildDescription(ModelEntity model, ConditionEnum condition) {
    return switch (condition) {
      case New -> "Brand new " + model.getName() + ", ready to drive.";
      case Used -> "Well maintained " + model.getName() + " in good condition.";
      case Damaged -> model.getName() + " with minor damage, repairable.";
      case Spares -> model.getName() + " suitable for spare parts.";
    };
  }
}