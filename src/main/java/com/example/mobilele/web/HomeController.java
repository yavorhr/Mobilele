package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.binding.offer.OffersQuickSearchBindingModel;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
  private final OfferService offerService;
  private final FeedbackService feedbackService;
  private final BrandService brandService;

  public HomeController(OfferService offerService, FeedbackService feedbackService, BrandService brandService) {
    this.offerService = offerService;
    this.feedbackService = feedbackService;
    this.brandService = brandService;
  }

  @ModelAttribute("offersFindBindingModel")
  public OffersFindBindingModel offersFindBindingModel() {
    return new OffersFindBindingModel();
  }

  @ModelAttribute("categories")
  public VehicleCategoryEnum[] getVehicleCategories() {
    return VehicleCategoryEnum.values();
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("latestOffers", offerService.findLatestOffers(6));
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("mostViewedOffers", offerService.findMostViewedOffers(6));
    model.addAttribute("feedbacks", feedbackService.findRecentFeedbacks(10));
    model.addAttribute("summary", feedbackService.getFeedbackSummary());

    return "index";
  }
}
