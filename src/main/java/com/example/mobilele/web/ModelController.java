package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    VehicleCategoryEnum vehicleCategoryEnum = VehicleCategoryEnum.from(vehicleType);

    List<String> models =
            modelService.findModelsByVehicleTypeAndBrand(
                    brand.toUpperCase(Locale.ROOT),
                    vehicleCategoryEnum);

    return ResponseEntity.ok(models);
  }
}
