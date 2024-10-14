package com.example.mobilele.service;

import com.example.mobilele.model.entity.BrandEntity;
import java.util.Optional;

public interface BrandService {
  void initBrands();

  Optional<BrandEntity> findBrandByName(String name);

}
