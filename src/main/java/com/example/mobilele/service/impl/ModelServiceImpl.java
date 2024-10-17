package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
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
  public List<String> findModelsPerBrand(String brand) {
    return this.modelRepository.findAllByBrandName(brand)
            .stream()
            .map(b -> b.substring(0, 1).toUpperCase() + b.substring(1))
            .collect(Collectors.toList());
  }

  @Override
  public ModelEntity findByName(String model) {
    return this.modelRepository.findByName(model);
  }

  @Override
  public void initModels() {
    if (modelRepository.count() == 0) {
      BrandEntity bmw = this.brandService.findBrandByName("bmw").get();

      ModelEntity x1 = new ModelEntity();

      x1
              .setName("x1")
              .setCategory(CategoryTypeEnum.SUV)
              .setStartYear(1976)
              .setEndYear(1990)
              .setBrand(bmw);

      ModelEntity x3 = new ModelEntity();
      x3
              .setName("x3")
              .setCategory(CategoryTypeEnum.SUV)
              .setStartYear(1976)
              .setEndYear(1995)
              .setBrand(bmw);

      modelRepository.saveAll(List.of(x1, x3));
    }
  }
}
