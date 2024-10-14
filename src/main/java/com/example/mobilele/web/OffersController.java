package com.example.mobilele.web;

import com.example.mobilele.model.dto.view.OfferViewModel;
import com.example.mobilele.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OffersController {
  private final OfferService offerService;

  public OffersController(OfferService offerService) {
    this.offerService = offerService;
  }

  // GET ALL OFFERS
  @GetMapping("/offers/all")
  public String getAllOffersPage(Model model) {
    List<OfferViewModel> offers = this.offerService.findAllOffers();
    model.addAttribute("offers", offers);

    return "offers";
  }

  // OFFER DETAILS MAPPINGS
  // * GET OFFER DETAILS
  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model) {
    OfferViewModel viewModel = this.offerService.findOfferById(id);

    model.addAttribute("offer", viewModel);

    return "details";
  }

  // * UPDATE OFFER
  @GetMapping("/offers/update/{id}")
  public String getOfferUpdatePage(@PathVariable Long id, Model model) {
    OfferViewModel offerModel = this.offerService.findOfferById(id);

    model.addAttribute("offer", offerModel);

    return "update";
  }

  // ADD OFFER
  @GetMapping("/offers/add")
  public String getAddOffersPage() {
    return "offer-add";
  }

  // DELETE OFFER
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }

}
