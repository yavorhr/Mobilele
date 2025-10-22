package com.example.mobilele.service;

import com.example.mobilele.model.service.brand.BrandServiceModel;
import com.example.mobilele.model.view.brand.BrandViewNameModel;
import com.example.mobilele.model.entity.BrandEntity;
import java.util.Collection;
import java.util.List;

public interface BrandService {
  void initBrands();

  BrandEntity findBrandByName(String name);

  List<BrandViewNameModel> findAllBrands();

  Collection<BrandServiceModel> getAllBrands();

}
