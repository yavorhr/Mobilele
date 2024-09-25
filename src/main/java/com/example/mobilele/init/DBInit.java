package com.example.mobilele.init;

import com.example.mobilele.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {
  private final UserService userService;
  private final UserRoleService  userRoleService;
  private final OfferService offerService;
  private final BrandService brandService;
  private final ModelService modelService;

  public DBInit(UserService userService, UserRoleService userRoleService, OfferService offerService, BrandService brandService, ModelService modelService) {
    this.userService = userService;
    this.userRoleService = userRoleService;
    this.offerService = offerService;

    this.brandService = brandService;
    this.modelService = modelService;
  }

  @Override
  public void run(String... args) throws Exception {
    userRoleService.initRoles();
    userService.initUsers();
    brandService.initBrands();
    modelService.initModels();
    offerService.initOffers();
  }
}
