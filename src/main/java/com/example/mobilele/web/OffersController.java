package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OfferUpdateBindingForm;
import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.entity.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
  public String getSearchFormByCategory(@PathVariable String vehicleType,
                                        Model model) {

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

    // ignore city field (fetched with AJAX)
    boolean hasRelevantErrors = bindingResult.getFieldErrors().stream()
            .anyMatch(e -> !"city".equals(e.getField()));

    if (hasRelevantErrors) {
      redirectAttributes
              .addFlashAttribute("offersFindBindingModel", offersFindBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offersFindBindingModel", bindingResult)
              .addFlashAttribute("models", offersFindBindingModel.getBrand() == null ? "" : this.modelService.findModelsByVehicleTypeAndBrand(offersFindBindingModel.getBrand(), vehicleCategoryEnum));

      return "redirect:/offers/find/" + vehicleType;
    }

    // pass to @Get
    redirectAttributes.addFlashAttribute("filters", offersFindBindingModel);

    return "redirect:/offers/" + vehicleType + "/" + offersFindBindingModel.getBrand().toLowerCase(Locale.ROOT) + "/" + offersFindBindingModel.getModel().toLowerCase(Locale.ROOT);
  }

  // 2. Get Offers - All
  @GetMapping("/offers/{vehicleType}/{brand}/{modelName}")
  public String showOffersByModel(
          @PathVariable String brand,
          @PathVariable String modelName,
          @PathVariable String vehicleType,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "1") int size,
          Model model) {

    VehicleCategoryEnum categoryEnum = null;

    if (vehicleType != null) {
      categoryEnum = VehicleCategoryEnum.valueOf(vehicleType.toUpperCase(Locale.ROOT));
    }

    String sortField = "creationDate".equals(sort) ? "created" : sort;
    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    Pageable pageable = PageRequest.of(page, size, sorting);

    // redirect attribute from @Post
    OffersFindBindingModel filters = (OffersFindBindingModel) model.asMap().get("filters");

    OffersFindServiceModel serviceModel = null;

    Page<OfferBaseViewModel> offersPage;

    if (filters != null) {
      serviceModel = this.modelMapper.map(filters, OffersFindServiceModel.class);
      offersPage = this.offerService.findOffersByFilters(serviceModel, categoryEnum, pageable);
    } else {
      offersPage = this.offerService.findByTypeBrandAndModel(categoryEnum, brand, modelName, pageable);
    }

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("vehicleType", vehicleType);
    model.addAttribute("brand", brand);
    model.addAttribute("model", modelName);
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("context", "model");

    return "offers";
  }

  @GetMapping("/offers/brands/{brand}/sort")
  public String showOffersByBrandSorted(
          @PathVariable String brand,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          Model model) {

    List<OfferBaseViewModel> offers = this.offerService.findOffersByBrand(brand)
            .stream()
            .map(o -> this.modelMapper.map(o, OfferBaseViewModel.class))
            .collect(Collectors.toList());

    offers = sortOffers(sort, dir, offers);

    model.addAttribute("brand", brand);
    model.addAttribute("offers", offers);
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);

    return "offers";
  }

  private List<OfferBaseViewModel> sortOffers(@RequestParam(defaultValue = "creationDate")
                                                      String sort, @RequestParam(defaultValue = "desc")
                                                      String dir, List<OfferBaseViewModel> offers) {

    offers = new ArrayList<>(offers);

    Comparator<OfferBaseViewModel> comparator = switch (sort) {
      case "price" -> Comparator.comparing(OfferBaseViewModel::getPrice);
      case "mileage" -> Comparator.comparing(OfferBaseViewModel::getMileage);
      default -> Comparator.comparing(OfferBaseViewModel::getCreated);
    };

    if ("desc".equalsIgnoreCase(dir)) comparator = comparator.reversed();
    offers.sort(comparator);
    return offers;
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
    model.addAttribute("context", "brand");
    model.addAttribute("offers", this.offerService.findOffersByBrand(brand.toUpperCase(Locale.ROOT)));
    return "offers";
  }

  // 2.2 Get Offers - By Id
  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model,
                                     Principal principal) {
    OfferViewModel viewModel =
            this.modelMapper.map(this.offerService.findOfferById(principal.getName(), id), OfferViewModel.class);

    model.addAttribute("offer", viewModel);
    model.addAttribute("isFavorite", this.offerService.doesOfferExistInUsersFavorites(id, principal.getName()));

    return "details";
  }

  // 3.1 Add Offer - GET
  @GetMapping("/offers")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());
    model.addAttribute("currentPage", "add");
    return "add";
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

  @GetMapping("/offers/my-offers")
  public String showMyOffers(
          Principal principal,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "1") int size,
          Model model) {

    String username = principal.getName();

    String sortField = "creationDate".equals(sort) ? "created" : sort;
    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    Pageable pageable = PageRequest.of(page, size, sorting);

    Page<OfferBaseViewModel> offersPage = offerService.findOffersByUserId(username, pageable);

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("context", "my");

    return "offers";
  }

  @GetMapping("/offers/favorites")
  public String showFavoriteOffers(
          Principal principal,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "1") int size,
          Model model) {

    String username = principal.getName();

    String sortField = "creationDate".equals(sort) ? "created" : sort;
    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    Pageable pageable = PageRequest.of(page, size, sorting);

    Page<OfferBaseViewModel> offersPage = offerService.findFavoriteOffers(username, pageable);

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("context", "favorites");

    return "offers";
  }

  @GetMapping("/offers/all")
  public String showAllOffers(
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "2") int size,
          Model model) {

    String sortField = "creationDate".equals(sort) ? "created" : sort;
    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    Pageable pageable = PageRequest.of(page, size, sorting);

    Page<OfferBaseViewModel> offersPage = offerService.findAllOffers(pageable);

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("context", "all");

    return "offers";
  }

   //4.1 Update offer - GET
//  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id )")
  @GetMapping("/offers/update/{id}")
  public String getOfferUpdatePage(@PathVariable Long id,
                                   @AuthenticationPrincipal MobileleUser principal,
                                   Model model) {

    OfferUpdateBindingForm offerBindingModel =
            this.modelMapper.map(this.offerService
                    .findOfferById(principal.getUsername(), id), OfferUpdateBindingForm.class);

    model.addAttribute("offerBindingModel", offerBindingModel);

    return "update";
  }

  // 4.2 Update offer -PATCH
//  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id)")
  @PatchMapping("/offers/update/{id}")
  public String updateOffer(@PathVariable Long id,
                            @AuthenticationPrincipal MobileleUser principal,
                            @Valid OfferUpdateBindingForm offerBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("offerBindingModel", offerBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offerBindingModel", bindingResult);

      return "redirect:/offers/update/errors/" + id;
    }

    this.offerService.updateOffer(this.modelMapper.map(offerBindingModel, OfferUpdateServiceModel.class), id);

    return "redirect:/offers/details/" + id;
  }

  @GetMapping("/offers/update/errors/{id}")
  public String editOfferErrors(@PathVariable Long id) {
    return "update";
  }

  // 5. Delete offer
  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id)")
  @DeleteMapping("/offers/{id}")
  public String deleteOffer(@AuthenticationPrincipal MobileleUser principal,
                            @PathVariable Long id,
                            RedirectAttributes redirectAttributes) {

    offerService.deleteById(id);

    redirectAttributes.addFlashAttribute("flashMessage", "✅ Offer deleted successfully!");
    redirectAttributes.addFlashAttribute("flashType", "success");

    return "redirect:/";
  }
}


