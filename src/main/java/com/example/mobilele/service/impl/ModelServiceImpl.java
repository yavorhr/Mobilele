package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Brand;
import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {
  private final ModelRepository modelRepository;
  private final BrandService brandService;

  public ModelServiceImpl(ModelRepository modelRepository, BrandService brandService) {
    this.modelRepository = modelRepository;
    this.brandService = brandService;
  }

  @Override
  public void initModels() {
    if (modelRepository.count() == 0) {
      Brand bmw = this.brandService.findBrandByName("bmw").get();

      ModelEntity x1 = new ModelEntity();

      x1
              .setName("x1")
              .setCategory(CategoryTypeEnum.SUV)
              .setImageUrl("https://vehicle-images.dealerinspire.com/8ad5-18003199/WBX73EF02P5Y24821/224e78f55fdf8a9751585798f8c3e968.jpg")
              .setStartYear(1976)
              .setBrand(bmw);

      ModelEntity x3 = new ModelEntity();
      x3
              .setName("x3")
              .setCategory(CategoryTypeEnum.SUV)
              .setImageUrl("https://www.netcarshow.com/BMW-X3-2025-1280-d0c4295e4d8b95d382e2bff356803ec6f0.jpg")
              .setStartYear(1976)
              .setBrand(bmw);

      modelRepository.saveAll(List.of(x1, x3));
    }
  }

  @Override
  public Optional<ModelEntity> findById(Long id) {
    return this.modelRepository.findById(id);
  }
}
