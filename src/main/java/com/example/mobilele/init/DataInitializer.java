package com.example.mobilele.init;

import com.example.mobilele.service.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
  private final UserRoleService userRoleService;
  private final UserService userService;
  private final BrandService brandService;
  private final ModelService modelService;
  private final OfferService offerService;

  public DataInitializer(UserRoleService userRoleService,
                         UserService userService,
                         BrandService brandService,
                         ModelService modelService,
                         OfferService offerService) {
    this.userRoleService = userRoleService;
    this.userService = userService;
    this.brandService = brandService;
    this.modelService = modelService;
    this.offerService = offerService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    userRoleService.initRoles();
    userService.initUsers();
    brandService.initBrands();
    modelService.initModels();
    offerService.initOffers();
  }
}

