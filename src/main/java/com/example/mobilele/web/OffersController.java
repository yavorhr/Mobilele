package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
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

  @ModelAttribute("offersFindBindingModel")
  public OffersFindBindingModel offersFindBindingModel() {
    return new OffersFindBindingModel();
  }

  @ModelAttribute("engines")
  public EngineEnum[] getEngines() {
    return EngineEnum.values();
  }

  @ModelAttribute("transmissions")
  public TransmissionType[] getTransmissions() {
    return TransmissionType.values();
  }

  @ModelAttribute("categories")
  public VehicleCategoryEnum[] getVehicleCategories() {
    return VehicleCategoryEnum.values();
  }

  @ModelAttribute("colors")
  public ColorEnum[] getColors() {
    return ColorEnum.values();
  }

  @ModelAttribute("conditions")
  public ConditionEnum[] getConditions() {
    return ConditionEnum.values();
  }

  // 1. Find offers - GET
  @GetMapping("/offers/find")
  public String getFindOffersView(Model model) {

    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("currentPage", "find");

    return "offers-find";
  }

  // 1.2 Find offers - POST
  @PostMapping("/offers/find")
  public String submitFindOffersForm(@Valid OffersFindBindingModel offerBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {

    
    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("offerBindingModel", offerBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult)
              .addFlashAttribute("models", offerBindingModel.getBrand() == null ? "" : this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));

      return "redirect:/offers/find";
    }

    //TODO : Is it needed ?
    model.addAttribute("brands", this.brandService.findAllBrands());

    return "redirect:offers/all";
  }

  // 2. Get Offers - All
  @GetMapping("/offers/all")
  public String getAllOffersPage(Model model) {
    List<OfferViewModel> offers = this.offerService.findAllOffers();
    model.addAttribute("offers", offers);

    return "offers";
  }

  //TODO ? What is this method for
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

  // 2.2 Get Offers - By Id
  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model, Principal principal) {
    OfferViewModel viewModel = this.modelMapper.map(this.offerService.findOfferById(principal.getName(), id), OfferViewModel.class);

    model.addAttribute("offer", viewModel);

    return "details";
  }

  // 3.1 Add Offer - GET
  @GetMapping("/offers/add")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("currentPage", "add");
    return "offer-add";
  }

  // 3.2 Add Offer - POST
  @PostMapping("/offers/add")
  public String addOffer(@Valid OfferAddBindingModel offerAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal MobileleUser user) throws IOException {

    if (offerAddBindingModel.getPictures() == null ||
            offerAddBindingModel.getPictures().isEmpty() ||
            offerAddBindingModel.getPictures().stream().allMatch(MultipartFile::isEmpty)) {

      bindingResult.rejectValue("pictures", "error.pictures", "At least one image is required");
    }

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
                    user.getUsername());

    return "redirect:/offers/details/" + serviceModel.getId();
  }

  // 4.1 Update offer - GET
  @GetMapping("/offers/update/{id}")
  public String getOfferUpdatePage(@PathVariable Long id, Model model, @AuthenticationPrincipal MobileleUser currentUser) {
    OfferAddBindingModel offerBindingModel =
            this.modelMapper.map(this.offerService.findOfferById(currentUser.getUsername(), id), OfferAddBindingModel.class);

    model.addAttribute("models", this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));
    model.addAttribute("offerBindingModel", offerBindingModel);

    return "update";
  }

  // 4.2 Update offer - PATCH
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

  // 5. Delete offer
  @PreAuthorize("@offerServiceImpl.isOwner(#principal.name, #id)")
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id, Principal principal) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }
}
