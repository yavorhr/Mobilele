package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.*;
import org.springframework.http.ResponseEntity;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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

  @ModelAttribute("countries")
  public CountryEnum[] getCountries() {
    return CountryEnum.values();
  }

  // I. Offers - GET
  // 1 Find offers
  @GetMapping("/offers/find")
  public String getFindOffersView(Model model) {
    model.addAttribute("currentPage", "find");
    return "offers-categories";
  }

  // 2. Find offers by categories
  @GetMapping("/offers/find/{vehicleType}")
  public String getSearchFormByCategory(@PathVariable String vehicleType, Model model) {

    model.addAttribute("vehicleType", vehicleType);
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("currentPage", "find");

    return "offers-find";
  }

  // II. Offers - POST
  // 1 Submit offers Search form
  @PostMapping("/offers/find/{vehicleType}")
  public String submitFindOffersForm(
          @PathVariable String vehicleType,
          @Valid OffersFindBindingModel offersFindBindingModel,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes) {

    VehicleCategoryEnum vehicleCategoryEnum = VehicleCategoryEnum.valueOf(vehicleType.toUpperCase(Locale.ROOT));

    boolean hasRelevantErrors = bindingResult.getFieldErrors().stream()
            .anyMatch(e -> !"city".equals(e.getField()));

    if (hasRelevantErrors) {
      redirectAttributes
              .addFlashAttribute("offersFindBindingModel", offersFindBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offersFindBindingModel", bindingResult)
              .addFlashAttribute("models", offersFindBindingModel.getBrand() == null ? "" : this.modelService.findModelsByVehicleTypeAndBrand(offersFindBindingModel.getBrand(), vehicleCategoryEnum));

      return "redirect:/offers/find/" + vehicleType;
    }

    List<OfferBaseViewModel> offers = offerService
            .findOffersByFilters(this.modelMapper.map(offersFindBindingModel, OffersFindServiceModel.class), vehicleCategoryEnum)
            .stream()
            .map(offerServiceModel -> modelMapper.map(offerServiceModel, OfferBaseViewModel.class))
            .collect(Collectors.toList());

    redirectAttributes.addFlashAttribute("offers", offers);

    return "redirect:/offers/" + offersFindBindingModel.getBrand().toUpperCase(Locale.ROOT) + "/" + offersFindBindingModel.getModel().toLowerCase(Locale.ROOT);
  }

  // 2. Get Offers - All
  @GetMapping("/offers/{brand}/{vehicleModel}")
  public String getAllOffersPage(@PathVariable String brand,
                                 @PathVariable String vehicleModel,
                                 Model model) {
    return "offers";
  }

  @ResponseBody
  @GetMapping("/locations/cities")
  public ResponseEntity<List<String>> getCities(@RequestParam("country") CountryEnum country) {
    List<String> cities = Arrays.stream(CityEnum.values())
            .filter(city -> city.getCountry().equals(country))
            .map(Enum::name)
            .toList();

    return ResponseEntity.ok(cities);
  }

  // 2. Get Offers - All
  @GetMapping("/offers/brands")
  public String getAllOffersPage() {
    return "offers-brands";
  }

  @GetMapping("/offers/brands/{brand}")
  public String getOffersByBrand(@PathVariable String brand, Model model) {
    model.addAttribute("brand", brand);

    model.addAttribute("offers", this.offerService.findOffersByBrand(brand.toUpperCase(Locale.ROOT)));
    return "offers";
  }

//  // 2. Get Offers - All
//  @GetMapping("/offers/all")
//  public String getAllOffersPage(Model model) {
//    List<OfferViewModel> offers = this.offerService.findAllOffers();
//    model.addAttribute("offers", offers);
//
//    return "offers";
//  }

//  //TODO ? What is this method for
//  @GetMapping("/offers")
//  public String getModelsByBrandName(@RequestParam String brand, Model model) {
//    List<OfferViewModel> offersByBrand = offerService
//            .findOffersByBrand(brand.toLowerCase())
//            .stream()
//            .map(offerServiceModel -> modelMapper.map(offerServiceModel, OfferViewModel.class))
//            .collect(Collectors.toList());
//
//    model.addAttribute("offers", offersByBrand);
//
//    return "offers";
//  }

  // 2.2 Get Offers - By Id
  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model, Principal principal) {
    OfferViewModel viewModel =
            this.modelMapper.map(this.offerService.findOfferById(principal.getName(), id), OfferViewModel.class);

    model.addAttribute("offer", viewModel);

    return "details";
  }

  // 3.1 Add Offer - GET
  @GetMapping("/offers")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("currentPage", "add");
    return "offer-add";
  }

  @PostMapping("/offers")
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
              .addFlashAttribute("models", offerAddBindingModel.getBrand() == null ? "" : this.modelService.findModelsByVehicleTypeAndBrand(
                      offerAddBindingModel.getBrand(),
                      offerAddBindingModel.getVehicleType()));

      return "redirect:/offers";
    }

    OfferAddServiceModel serviceModel =
            this.offerService.addOffer(
                    this.modelMapper.map(offerAddBindingModel, OfferAddServiceModel.class),
                    user.getUsername());

    return "redirect:/offers/details/" + serviceModel.getId();
  }

  // 4.1 Update offer - GET
//  @GetMapping("/offers/update/{id}")
//  public String getOfferUpdatePage(@PathVariable Long id, Model model, @AuthenticationPrincipal MobileleUser currentUser) {
//    OfferAddBindingModel offerBindingModel =
//            this.modelMapper.map(this.offerService.findOfferById(currentUser.getUsername(), id), OfferAddBindingModel.class);
//
//    model.addAttribute("models", this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));
//    model.addAttribute("offerBindingModel", offerBindingModel);
//
//    return "update";
//  }

  // 4.2 Update offer - PATCH
//  @PatchMapping("/offers/update/{id}")
//  public String updateOffer(@PathVariable Long id,
//                            @Valid OfferAddBindingModel offerBindingModel,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes) {
//
//    if (bindingResult.hasErrors()) {
//      redirectAttributes
//              .addFlashAttribute("offerBindingModel", offerBindingModel)
//              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult)
//              .addFlashAttribute("models", this.modelService.findModelsPerBrand(offerBindingModel.getBrand()));
//
//      return "redirect:/offers/update/errors/" + id;
//    }
//
//    //TODO:
//    //this.offerService.updateOffer(this.modelMapper.map(offerBindingModel, OfferUpdateServiceModel.class), currentUser.getId());
//
//    return "redirect:/offers/details/" + id;
//  }

  @GetMapping("/offers/update/errors/{id}")
  public String editOfferErrors(@PathVariable Long id) {
    return "update";
  }

  // 5. Delete offer
  @PreAuthorize("@offerServiceImpl.ownerOrIsAdmin(#principal.name, #id)")
  @DeleteMapping("/offers/{id}")
  public String deleteOfferById(@PathVariable Long id, Principal principal) {
    this.offerService.deleteById(id);

    return "redirect:/offers/all";
  }
}
