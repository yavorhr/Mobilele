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
  public List<ModelEntity> findAll() {
    return this.modelRepository.findAll();
  }

  @Override
  public void initModels() {
    if (modelRepository.count() > 0) {
      return;
    }

    Map<String, List<ModelEntity>> seedData = Map.ofEntries(

            Map.entry("BMW", List.of(
                    new ModelEntity("3 Series", VehicleCategoryEnum.Car , 2018),
                    new ModelEntity("1 Series", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("X3", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("X5", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("AUDI", List.of(
                    new ModelEntity("A4", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("A6", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("Q3", VehicleCategoryEnum.SUV, 2018),
                    new ModelEntity("Q7", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("TOYOTA", List.of(
                    new ModelEntity("Corolla", VehicleCategoryEnum.Car, 2017),
                    new ModelEntity("Camry", VehicleCategoryEnum.Car, 2020),
                    new ModelEntity("RAV4", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("Highlander", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("MERCEDES", List.of(
                    new ModelEntity("C-Class", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("E-Class", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("GLA", VehicleCategoryEnum.SUV, 2018),
                    new ModelEntity("GLE", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("VOLKSWAGEN", List.of(
                    new ModelEntity("Golf", VehicleCategoryEnum.Car, 2018),
                    new ModelEntity("Passat", VehicleCategoryEnum.Car, 2020),
                    new ModelEntity("Tiguan", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("Touareg", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("DACIA", List.of(
                    new ModelEntity("Logan", VehicleCategoryEnum.Car, 2017),
                    new ModelEntity("Sandero", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("Dustrer", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("Bigster", VehicleCategoryEnum.SUV, 2023)
            )),

            Map.entry("TESLA", List.of(
                    new ModelEntity("Model 3", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("Model S", VehicleCategoryEnum.Car, 2022),
                    new ModelEntity("Cybertruck", VehicleCategoryEnum.SUV, 2022),
                    new ModelEntity("Model X", VehicleCategoryEnum.SUV, 2023)
            )),

            Map.entry("HONDA", List.of(
                    new ModelEntity("Civic", VehicleCategoryEnum.Car, 2018),
                    new ModelEntity("Accord", VehicleCategoryEnum.Car, 2020),
                    new ModelEntity("CR-V", VehicleCategoryEnum.SUV, 2019),
                    new ModelEntity("HR-V", VehicleCategoryEnum.SUV, 2021)
            )),

            Map.entry("FORD", List.of(
                    new ModelEntity("Focus", VehicleCategoryEnum.Car, 2018),
                    new ModelEntity("Mondeo", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("Kuga", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("Explorer", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("NISSAN", List.of(
                    new ModelEntity("Sentra", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("Altima", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("Qashqai", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("X-Trail", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("HYUNDAI", List.of(
                    new ModelEntity("Elantra", VehicleCategoryEnum.Car, 2019),
                    new ModelEntity("Sonata", VehicleCategoryEnum.Car, 2021),
                    new ModelEntity("Tucson", VehicleCategoryEnum.SUV, 2020),
                    new ModelEntity("Santa Fe", VehicleCategoryEnum.SUV, 2022)
            )),

            Map.entry("KIA", List.of(
                    new ModelEntity("Ceed", VehicleCategoryEnum.Car, 2018),
                    new ModelEntity("K5", VehicleCategoryEnum.Car, 2020),
                    new ModelEntity("Sportage", VehicleCategoryEnum.SUV, 2021),
                    new ModelEntity("Sorento", VehicleCategoryEnum.SUV, 2022)
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

