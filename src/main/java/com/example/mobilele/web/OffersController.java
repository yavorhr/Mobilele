package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.dto.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.dto.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffersController {
  private final OfferService offerService;
  private final BrandService brandService;
  private final ModelMapper modelMapper;
  private final ModelService modelService;

  public OffersController(OfferService offerService, BrandService brandService, ModelMapper modelMapper, ModelService modelService) {
    this.offerService = offerService;
    this.brandService = brandService;
    this.modelMapper = modelMapper;
    this.modelService = modelService;
  }

  @ModelAttribute("offerBindingModel")
  public OfferAddBindingModel offerBindingModel() {
    return new OfferAddBindingModel();
  }

  @ModelAttribute("engines")
  public EngineEnum[] getEngines() {
    return EngineEnum.values();
  }

  @ModelAttribute("transmissions")
  public TransmissionType[] getTransmissions() {
    return TransmissionType.values();
  }

  // GET
  @GetMapping("/offers/all")
  public String getAllOffersPage(Model model) {
    List<OfferViewModel> offers = this.offerService.findAllOffers();
    model.addAttribute("offers", offers);

    return "offers";
  }

  @GetMapping("/offers")
  public String getModelsByBrandName(@RequestParam String brand, Model model) {
    List<OfferViewModel> offersByBrand = offerService
            .findOffersByBrand(brand.toLowerCase())
            .stream()
            .map(offerServiceModel -> modelMapper.map(offerServiceModel, OfferViewModel.class))
            .collect(Collectors.toList());

    model.addAttribute("offers", offersByBrand);

    return "offers";
  }

  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model) {
    OfferViewModel viewModel = this.modelMapper.map(this.offerService.findOfferById(id), OfferViewModel.class);

    model.addAttribute("offer", viewModel);

    return "details";
  }

  // UPDATE OFFER
  @GetMapping("/offers/update/{id}")
  public String getOfferUpdatePage(@PathVariable Long id, Model model) {
    OfferAddBindingModel offerBindingModel =
            this.modelMapper.map(this.offerService.findOfferById(id), OfferAddBindingModel.class);

    model.addAttribute("models", this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));
    model.addAttribute("offerBindingModel", offerBindingModel);

    return "update";
  }

  @PatchMapping("/offers/update/{id}")
  public String updateOffer(@PathVariable Long id,
                            @Valid OfferAddBindingModel offerBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("offerBindingModel", offerBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult)
              .addFlashAttribute("models", this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));

      return "redirect:/offers/update/errors/" + id;
    }

  //TODO:
    //this.offerService.updateOffer(this.modelMapper.map(offerBindingModel, OfferUpdateServiceModel.class), currentUser.getId());

    return "redirect:/offers/details/" + id;
  }

  @GetMapping("/offers/update/errors/{id}")
  public String editOfferErrors(@PathVariable Long id) {
    return "update";
  }

  // ADD OFFER
  @GetMapping("/offers/add")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());

    return "offer-add";
  }

  @PostMapping("/offers/add")
  public String addOffer(@Valid OfferAddBindingModel offerAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal MobileleUser user) {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("offerBindingModel", offerAddBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult)
              .addFlashAttribute("models", offerAddBindingModel.getBrand() == null ? "" : this.modelService.findModelsPerBrand(offerAddBindingModel.getBrand()));

      return "redirect:/offers/add";
    }

    OfferAddServiceModel serviceModel =
            this.offerService.addOffer(
                    this.modelMapper.map(offerAddBindingModel, OfferAddServiceModel.class),
                    user.getUserIdentifier());

    return "redirect:/offers/details/" + serviceModel.getId();
  }

  // DELETE OFFER

  @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id)")
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id, Principal principal) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }
}
