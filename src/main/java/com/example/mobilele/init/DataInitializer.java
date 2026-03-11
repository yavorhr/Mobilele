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
  private final FeedbackService feedbackService;
  private final SoldOfferService soldOfferService;

  public DataInitializer(UserRoleService userRoleService,
                         UserService userService,
                         BrandService brandService,
                         ModelService modelService,
                         OfferService offerService, FeedbackService feedbackService, SoldOfferService soldOfferService) {
    this.userRoleService = userRoleService;
    this.userService = userService;
    this.brandService = brandService;
    this.modelService = modelService;
    this.offerService = offerService;
    this.feedbackService = feedbackService;
    this.soldOfferService = soldOfferService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    userRoleService.seedRoles();
    userService.seedUsers();
    brandService.seedBrands();
    modelService.seedModels();
    offerService.seedOffers();
    soldOfferService.seedSoldOffers();
    feedbackService.seedFeedbacks();
  }
}

