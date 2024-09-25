package com.example.mobilele.service;

import com.example.mobilele.model.entity.Brand;

import java.util.Optional;

public interface BrandService {
  void initBrands();

  Optional<Brand> findBrandByName(String name);
}
