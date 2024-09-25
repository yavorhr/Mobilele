package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Brand;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
  private final BrandRepository brandRepository;

  public BrandServiceImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  public void initBrands() {
    if (brandRepository.count() == 0) {
      Brand bmw = new Brand();
      bmw.setName("bmw");

      this.brandRepository.save(bmw);
    }
  }
}
