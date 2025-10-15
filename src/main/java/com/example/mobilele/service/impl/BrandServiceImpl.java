package com.example.mobilele.service.impl;

import com.example.mobilele.model.service.brand.BrandServiceModel;
import com.example.mobilele.model.view.brand.BrandViewNameModel;
import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
  public Optional<BrandEntity> findBrandByName(String name) {
    return this.brandRepository.findBrandByNameIgnoreCase(name);
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

  @Override
  public void initBrands() {
    if (this.brandRepository.count() == 0) {
      BrandEntity bmw = new BrandEntity();
      bmw.setName("BMW");

      BrandEntity audi = new BrandEntity();
      audi.setName("AUDI");

      BrandEntity toyota = new BrandEntity();
      toyota.setName("TOYOTA");

      this.brandRepository.saveAll(List.of(bmw, audi, toyota));
    }
  }
}
