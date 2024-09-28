package com.example.mobilele.web;

import com.example.mobilele.model.dto.view.OfferViewModel;
import com.example.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OffersController {
  private final OfferService offerService;

  public OffersController(OfferService offerService) {
    this.offerService = offerService;
  }

  @GetMapping("/offers/all")
  public String getAllOffersPage(Model model) {
    List<OfferViewModel> offers = this.offerService.findAllOffers();
    model.addAttribute("offers", offers);
    
    return "offers";
  }

  @GetMapping("/offers/add")
  public String getAddOffersPage() {
    return "offer-add";
  }
}
