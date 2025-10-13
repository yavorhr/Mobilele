package com.example.mobilele.util;

import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Locale;

public final class ProjectHelpers {

  private ProjectHelpers() {
  }

  public static VehicleCategoryEnum parseToVehicleEnum(String input) {
    if (input == null || input.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle type cannot be empty");
    }

    String vehicle = input.trim().toLowerCase(Locale.ROOT);

    if (vehicle.endsWith("es")) {
      vehicle = vehicle.substring(0, vehicle.length() - 2);
    } else if (vehicle.endsWith("s")) {
      vehicle = vehicle.substring(0, vehicle.length() - 1);
    }

    vehicle = vehicle.toUpperCase(Locale.ROOT);

    try {
      return VehicleCategoryEnum.valueOf(vehicle);
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid vehicle type: " + input);
    }
  }
}
