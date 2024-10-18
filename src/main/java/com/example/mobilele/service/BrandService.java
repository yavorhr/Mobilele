package com.example.mobilele.service;

import com.example.mobilele.model.dto.service.brand.BrandServiceModel;
import com.example.mobilele.model.dto.view.brand.BrandViewNameModel;
import com.example.mobilele.model.entity.BrandEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BrandService {
  void initBrands();

  Optional<BrandEntity> findBrandByName(String name);

  List<BrandViewNameModel> findAllBrands();

  Collection<BrandServiceModel> getAllBrands();

}
