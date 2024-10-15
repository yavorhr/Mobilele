package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.offer.OfferBindingModel;
import com.example.mobilele.model.dto.view.OfferViewModel;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OffersController {
  private final OfferService offerService;
  private final BrandService brandService;

  public OffersController(OfferService offerService, BrandService brandService) {
    this.offerService = offerService;
    this.brandService = brandService;
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

  @ModelAttribute("offerBindingModel")
  public OfferBindingModel offerBindingModel() {
    return new OfferBindingModel();
  }

  @GetMapping("/offers/add")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("engines", EngineEnum.values());
    model.addAttribute("transmissions", TransmissionType.values());

    return "offer-add";
  }

  @PostMapping("/offers/add")
  public String addOffer(@Valid OfferBindingModel offerBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("offerBindingModel", offerBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult);

      return "redirect:/offers/add";
    }
    System.out.println();
    return "";

  }

  // DELETE OFFER
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }

}
