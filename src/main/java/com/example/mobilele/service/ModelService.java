package com.example.mobilele.service;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;

import java.util.List;
import java.util.Optional;

public interface ModelService {
  void initModels();

  Optional<ModelEntity> findById(Long id);

//  List<String> findModelsPerBrand(String brand);

  Optional<ModelEntity> findByName(String model);

  List<String> findModelsByVehicleTypeAndBrand(String brand, VehicleCategoryEnum vehicle);
}
