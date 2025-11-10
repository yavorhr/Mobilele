package com.example.mobilele.web;

import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private final OfferService offerService;
  private final FeedbackService feedbackService;

  public HomeController(OfferService offerService, FeedbackService feedbackService) {

    this.offerService = offerService;
    this.feedbackService = feedbackService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("latestOffers", offerService.findLatestOffers(6));
    model.addAttribute("latestReviews", feedbackService.findRecentFeedbacks(10));

    return "index";
  }
}
