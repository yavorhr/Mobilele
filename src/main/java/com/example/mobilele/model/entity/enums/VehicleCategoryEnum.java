package com.example.mobilele.model.entity.enums;

import java.util.Arrays;

public enum VehicleCategoryEnum {
  Car("car"),
  SUV("SUV"),
  Motorcycle("motorcycle"),
  Truck("truck"),
  Caravan("caravan"),
  Bus("bus");

  private final String label;

  VehicleCategoryEnum(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static VehicleCategoryEnum from(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Vehicle category is required");
    }

    return Arrays.stream(values())
            .filter(v -> v.name().equalsIgnoreCase(value.trim()))
            .findFirst()
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Invalid vehicle category: " + value
                    )
            );
  }
}
