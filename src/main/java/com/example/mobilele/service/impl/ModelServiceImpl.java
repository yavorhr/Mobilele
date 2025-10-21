package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
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
  public Optional<ModelEntity> findByName(String model) {
    return this.modelRepository.findByName(model);
  }

  @Override
  public List<String> findModelsByVehicleTypeAndBrand(String brand, VehicleCategoryEnum vehicleType) {
    return this.modelRepository
            .findAllByBrandNameAndVehicleType(brand.toLowerCase(), vehicleType)
            .stream()
            .map(b -> b.substring(0, 1).toUpperCase() + b.substring(1))
            .collect(Collectors.toList());
  }

  @Override
  public void initModels() {
    if (modelRepository.count() == 0) {
      BrandEntity bmw = this.brandService.findBrandByName("BMW").get();

      ModelEntity m1 = new ModelEntity();

      m1
              .setName("M1")
              .setBrand(bmw)
              .setVehicleType(VehicleCategoryEnum.CAR)
              .setYear(1999);

      ModelEntity x3 = new ModelEntity();

      x3
              .setName("X3")
              .setBrand(bmw)
              .setVehicleType(VehicleCategoryEnum.SUV)
              .setYear(2003);

      BrandEntity audi = this.brandService.findBrandByName("AUDI").get();

      ModelEntity q5 = new ModelEntity();
      q5
              .setName("Q5")
              .setBrand(audi)
              .setVehicleType(VehicleCategoryEnum.SUV)
              .setYear(2015);

      BrandEntity toyota = this.brandService.findBrandByName("TOYOTA").get();

      ModelEntity rav4 = new ModelEntity();
      rav4
              .setName("RAV4")
              .setBrand(toyota)
              .setVehicleType(VehicleCategoryEnum.SUV)
              .setYear(2023);

      modelRepository.saveAll(List.of(m1, x3, q5, rav4));
    }
  }
}
