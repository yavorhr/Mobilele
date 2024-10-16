package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.OfferBindingModel;
import com.example.mobilele.model.dto.service.OfferAddServiceModel;
import com.example.mobilele.model.dto.view.OfferViewModel;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.user.CurrentUser;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
  private final ModelMapper modelMapper;
  private final CurrentUser currentUser;

  public OffersController(OfferService offerService, BrandService brandService, ModelMapper modelMapper, CurrentUser currentUser) {
    this.offerService = offerService;
    this.brandService = brandService;
    this.modelMapper = modelMapper;
    this.currentUser = currentUser;
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
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult)
              .addFlashAttribute("brands", this.brandService.findAllBrands());
      return "redirect:/offers/add";
    }

    OfferAddServiceModel serviceModel =
            this.offerService.addOffer(
                    this.modelMapper.map(offerBindingModel, OfferAddServiceModel.class),
                    currentUser.getId());

    return "redirect:/offers/details/" + serviceModel.getId();
  }

  // DELETE OFFER
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }

}
