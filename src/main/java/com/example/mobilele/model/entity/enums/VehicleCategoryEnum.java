package com.example.mobilele.model.entity.enums;

import java.util.Arrays;

public enum VehicleCategoryEnum {
  Car("car", "fa-solid fa-car-side"),
  SUV("SUV", "fa-solid fa-truck-pickup"),
  Motorcycle("motorcycle", "fas fa-motorcycle"),
  Truck("truck", "fas fa-truck"),
  Caravan("caravan", "fa-solid fa-caravan"),
  Bus("bus", "fa-solid fa-bus-simple");

  private final String label;
  private final String iconClass;

  VehicleCategoryEnum(String label, String iconClass) {
    this.label = label;
    this.iconClass = iconClass;
  }

  public String getIconClass() {
    return iconClass;
  }

  public String getLabel() {
    return label;
  }

  public static VehicleCategoryEnum from(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Vehicle category is required");
    }

    return Arrays.stream(values())
            .filter(v -> v.getLabel().equalsIgnoreCase(value.trim()))
            .findFirst()
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Invalid vehicle category: " + value
                    )
            );
  }
}
