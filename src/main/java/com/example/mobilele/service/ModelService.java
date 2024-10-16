package com.example.mobilele.service;

import com.example.mobilele.model.entity.ModelEntity;

import java.util.List;
import java.util.Optional;

public interface ModelService {
  void initModels();

  Optional<ModelEntity> findById(Long id);

  List<String> findModelsPerBrand(String brand);

  ModelEntity findByName(String model);
}
