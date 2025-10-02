package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.model.entity.ModelEntity;
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
    return this.modelRepository
            .findAllByBrandName(brand.toLowerCase())
            .stream()
            .map(b -> b.substring(0, 1).toUpperCase() + b.substring(1))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<ModelEntity> findByName(String model) {
    return this.modelRepository.findByName(model);
  }

  @Override
  public void initModels() {
    if (modelRepository.count() == 0) {
      BrandEntity bmw = this.brandService.findBrandByName("bmw").get();

      ModelEntity m3 = new ModelEntity();

      m3
              .setName("m3")
              .setBrand(bmw);

      ModelEntity x3 = new ModelEntity();

      x3
              .setName("x3")
              .setBrand(bmw);

      BrandEntity audi = this.brandService.findBrandByName("audi").get();

      ModelEntity q5 = new ModelEntity();
      q5
              .setName("q5")
              .setBrand(audi);

      BrandEntity toyota = this.brandService.findBrandByName("toyota").get();

      ModelEntity rav4 = new ModelEntity();
      rav4
              .setName("RAV4")
              .setBrand(toyota);

      modelRepository.saveAll(List.of(m3, x3, x3, q5, rav4));
    }
  }
}
