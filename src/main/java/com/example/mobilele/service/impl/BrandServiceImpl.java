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

  @Override
  public void initBrands() {
    if (this.brandRepository.count() == 0) {
      BrandEntity bmw = initBrand("BMW");
      BrandEntity audi = initBrand("AUDI");
      BrandEntity toyota = initBrand("TOYOTA");
      BrandEntity mercedes = initBrand("MERCEDES");
      BrandEntity volkswagen = initBrand("VOLKSWAGEN");
      BrandEntity dacia = initBrand("DACIA");
      BrandEntity tesla = initBrand("TESLA");
      BrandEntity honda = initBrand("HONDA");
      BrandEntity ford = initBrand("FORD");
      BrandEntity nissan = initBrand("NISSAN");
      BrandEntity hyundai = initBrand("HYUNDAI");
      BrandEntity kia = initBrand("KIA");

      this.brandRepository.saveAll(List.of(
              bmw, audi, toyota, mercedes, volkswagen, dacia, tesla, honda, ford, nissan, hyundai, kia));
    }
  }

  private BrandEntity initBrand(String brand) {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setName(brand);

    return brandEntity;
  }
}
