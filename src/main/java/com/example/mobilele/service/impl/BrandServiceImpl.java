package com.example.mobilele.service.impl;

import com.example.mobilele.model.service.brand.BrandServiceModel;
import com.example.mobilele.model.view.brand.BrandViewNameModel;
import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
  private final BrandRepository brandRepository;
  private final ModelMapper modelMapper;

  public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
    this.brandRepository = brandRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public BrandEntity findBrandByName(String name) {
    return this.brandRepository
            .findBrandByNameIgnoreCase(name)
            .orElseThrow(() -> new ObjectNotFoundException("Brand with name " + name + " does not exist!"));
  }

  @Override
  public List<BrandViewNameModel> findAllBrands() {
    return this.brandRepository
            .findAll()
            .stream()
            .map(brandEntity -> this.modelMapper.map(brandEntity, BrandViewNameModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public Collection<BrandServiceModel> getAllBrands() {
    return this.brandRepository
            .findAllBrandsWithModels()
            .stream()
            .map(brand -> this.modelMapper.map(brand, BrandServiceModel.class))
            .collect(Collectors.toList());
  }

  // Init Brands
  @Override
  public void initBrands() {
    if (brandRepository.count() > 0) {
      return;
    }

    List<String> brandNames = List.of(
            "BMW",
            "Audi",
            "Toyota",
            "Mercedes",
            "Volkswagen",
            "Dacia",
            "Tesla",
            "Honda",
            "Ford",
            "Nissan",
            "Hyundai",
            "KIA"
    );

    List<BrandEntity> brands = brandNames.stream()
            .map(this::initBrand)
            .toList();

    brandRepository.saveAll(brands);
  }

  private BrandEntity initBrand(String brand) {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setName(brand);

    return brandEntity;
  }
}
