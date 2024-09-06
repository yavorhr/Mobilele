package com.example.mobilele.init;

import com.example.mobilele.model.entity.Brand;
import com.example.mobilele.model.entity.Model;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.repository.ModelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
public class DBInit implements CommandLineRunner {
  private final BrandRepository brandRepository;
  private final ModelRepository modelRepository;

  public DBInit(BrandRepository brandRepository, ModelRepository modelRepository) {
    this.brandRepository = brandRepository;
    this.modelRepository = modelRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Brand brand = new Brand();
    brand.setName("BMW");

    Model x1 = new Model();
    x1.setBrand(brand);
    x1.setCategory(CategoryTypeEnum.CAR);
    x1.setName("x5");
    x1.setStartYear(2010);
    x1.setImageUrl("https://www.bmw.bg/bg/all-models/m-series/bmw-x1-m35i/2023/bmw-x1-m35i-overview.html");

    this.modelRepository.save(x1);

//    Model model = modelRepository.findById(2L).orElse(null);
//    model.setName("test");
//    this.modelRepository.save(model);
  }

}
