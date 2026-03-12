package com.example.mobilele.web;

import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private final OfferService offerService;
  private final SoldOfferService soldOfferService;
  private final FeedbackService feedbackService;

  public HomeController(OfferService offerService, SoldOfferService soldOfferService, FeedbackService feedbackService) {
    this.offerService = offerService;
    this.soldOfferService = soldOfferService;
    this.feedbackService = feedbackService;
  }

  @GetMapping("/")
  public String index(Model model) {
    int startYear = 2025;

    model.addAttribute("latestOffers", offerService.findLatestOffers(5));
    model.addAttribute("mostViewedOffers", offerService.findMostViewedOffers(5));
    model.addAttribute("feedbacks", feedbackService.findRecentFeedbacks(10));
    model.addAttribute("summary", feedbackService.getFeedbackSummary());
    model.addAttribute("startYear", startYear);
    model.addAttribute("soldCount", soldOfferService.getSoldVehiclesCount());

    return "index";
  }

  @GetMapping("/sellers/top")
  public String topSellers(Model model) {
    model.addAttribute("topSellers", offerService.getTop20Sellers());
    return "top-sellers";
  }
}


