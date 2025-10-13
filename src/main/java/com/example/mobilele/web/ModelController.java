package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.service.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@RestController
public class ModelController {
  private final ModelService modelService;

  public ModelController(ModelService modelService) {
    this.modelService = modelService;
  }

  @GetMapping("/models")
  public ResponseEntity<List<String>> getModelsByVehicleAndBrand(
          @RequestParam String brand,
          @RequestParam String vehicleType) {

    VehicleCategoryEnum category = parseVehicleCategory(vehicleType);

    List<String> models =
            modelService.findModelsByVehicleTypeAndBrand(
                    brand,
                    category);

    return ResponseEntity.ok(models);
  }

  // Helpers
  private VehicleCategoryEnum parseVehicleCategory(String input) {
    if (input == null || input.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle type cannot be empty");
    }

    String vehicle = input.trim().toLowerCase();

    if (vehicle.endsWith("es")) {
      vehicle = vehicle.substring(0, vehicle.length() - 2);
    } else if (vehicle.endsWith("s")) {
      vehicle = vehicle.substring(0, vehicle.length() - 1);
    }

    vehicle = vehicle.toUpperCase();

    try {
      return VehicleCategoryEnum.valueOf(vehicle);
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid vehicle type: " + input);
    }
  }
}
