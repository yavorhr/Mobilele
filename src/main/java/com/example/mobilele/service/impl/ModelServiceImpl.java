package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService {
  private final ModelRepository modelRepository;
  private final BrandService brandService;

  public ModelServiceImpl(ModelRepository modelRepository, BrandService brandService) {
    this.modelRepository = modelRepository;
    this.brandService = brandService;
  }

  @Override
  public Optional<ModelEntity> findById(Long id) {
    return this.modelRepository.findById(id);
  }

  @Override
  public ModelEntity findByName(String model) {
    return this.modelRepository.findByName(model)
            .orElseThrow(() -> new ObjectNotFoundException("Model with name " + model + " was not found!"));
  }

  @Override
  public List<String> findModelsByVehicleTypeAndBrand(String brand, VehicleCategoryEnum vehicleType) {
    List<String> results = this.modelRepository.findAllByBrandNameAndVehicleType(brand, vehicleType);

    if (results == null || results.isEmpty()) {
      return Collections.emptyList();
    }

    return results.stream()
            .map(b -> b.substring(0, 1).toUpperCase() + b.substring(1))
            .collect(Collectors.toList());
  }

  @Override
  public void initModels() {
    if (modelRepository.count() > 0) {
      return;
    }

      Map<String, List<ModelEntity>> seedData = Map.of(
              "BMW", List.of(
                      new ModelEntity("M1", VehicleCategoryEnum.CAR, 2015),
                      new ModelEntity("M3", VehicleCategoryEnum.CAR, 2025),
                      new ModelEntity("X3", VehicleCategoryEnum.SUV, 2010),
                      new ModelEntity("X5", VehicleCategoryEnum.SUV, 2011)
              ),
              "AUDI", List.of(
                      new ModelEntity("Q3", VehicleCategoryEnum.SUV, 2015),
                      new ModelEntity("Q5", VehicleCategoryEnum.SUV, 2025),
                      new ModelEntity("TT", VehicleCategoryEnum.CAR, 2010),
                      new ModelEntity("A8", VehicleCategoryEnum.CAR, 2011)
              )
              // ➕ add the remaining 10 brands here
      );

      List<ModelEntity> modelsContainer = new ArrayList<>();

      seedData
              .forEach((brandName, models) -> {
        BrandEntity brand = brandService.findBrandByName(brandName);

        models.forEach(model -> {
          modelsContainer.add(
                  createModel(brand, model)
          );
        });
      });

      modelRepository.saveAll(modelsContainer);
    }

    private ModelEntity createModel (BrandEntity brand, ModelEntity model){
      return new ModelEntity()
              .setName(model.getName())
              .setBrand(brand)
              .setVehicleType(model.getVehicleType())
              .setYear(model.getYear());
    }
  }

