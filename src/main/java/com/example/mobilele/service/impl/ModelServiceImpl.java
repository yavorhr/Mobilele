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

    Map<String, List<ModelEntity>> seedData = Map.ofEntries(

            Map.entry("BMW", List.of(
                    new ModelEntity("3 SERIES", VehicleCategoryEnum.CAR, 2018),
                    new ModelEntity("5 SERIES", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("X3", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("X5", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("AUDI", List.of(
                    new ModelEntity("A4", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("A6", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("Q3", VehicleCategoryEnum.SUV, 2018),
                    new ModelEntity("Q7", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("TOYOTA", List.of(
                    new ModelEntity("COROLLA", VehicleCategoryEnum.CAR, 2017),
                    new ModelEntity("CAMRY", VehicleCategoryEnum.CAR, 2020),
                    new ModelEntity("RAV4", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("HIGHLANDER", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("MERCEDES", List.of(
                    new ModelEntity("C-CLASS", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("E-CLASS", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("GLA", VehicleCategoryEnum.SUV, 2018),
                    new ModelEntity("GLE", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("VOLKSWAGEN", List.of(
                    new ModelEntity("GOLF", VehicleCategoryEnum.CAR, 2018),
                    new ModelEntity("PASSAT", VehicleCategoryEnum.CAR, 2020),
                    new ModelEntity("TIGUAN", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("TOUAREG", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("DACIA", List.of(
                    new ModelEntity("LOGAN", VehicleCategoryEnum.CAR, 2017),
                    new ModelEntity("SANDERO", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("DUSTER", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("BIGSTER", VehicleCategoryEnum.SUV, 2023)
            )),

            Map.entry("TESLA", List.of(
                    new ModelEntity("MODEL 3", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("MODEL S", VehicleCategoryEnum.CAR, 2022),
                    new ModelEntity("MODEL Y", VehicleCategoryEnum.SUV, 2022),
                    new ModelEntity("MODEL X", VehicleCategoryEnum.SUV, 2023)
            )),

            Map.entry("HONDA", List.of(
                    new ModelEntity("CIVIC", VehicleCategoryEnum.CAR, 2018),
                    new ModelEntity("ACCORD", VehicleCategoryEnum.CAR, 2020),
                    new ModelEntity("CR-V", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("HR-V", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("FORD", List.of(
                    new ModelEntity("FOCUS", VehicleCategoryEnum.CAR, 2018),
                    new ModelEntity("MONDEO", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("KUGA", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("EXPLORER", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("NISSAN", List.of(
                    new ModelEntity("SENTRA", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("ALTIMA", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("QASHQAI", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("X-TRAIL", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("HYUNDAI", List.of(
                    new ModelEntity("ELANTRA", VehicleCategoryEnum.CAR, 2019),
                    new ModelEntity("SONATA", VehicleCategoryEnum.CAR, 2021),
                    new ModelEntity("TUCSON", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("SANTA FE", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("KIA", List.of(
                    new ModelEntity("CEED", VehicleCategoryEnum.CAR, 2018),
                    new ModelEntity("OPTIMA", VehicleCategoryEnum.CAR, 2020),
                    new ModelEntity("SPORTAGE", VehicleCategoryEnum.SUV, 2021),
                    new ModelEntity("SORENTO", VehicleCategoryEnum.SUV, 2022)
            ))
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

  private ModelEntity createModel(BrandEntity brand, ModelEntity model) {
    return new ModelEntity()
            .setName(model.getName())
            .setBrand(brand)
            .setVehicleType(model.getVehicleType())
            .setYear(model.getYear());
  }
}

