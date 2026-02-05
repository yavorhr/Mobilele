package com.example.mobilele.model.entity.enums;

import java.util.Arrays;

public enum VehicleCategoryEnum {
  Car,
  Truck,
  Motorcycle,
  SUV,
  Bus;

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
