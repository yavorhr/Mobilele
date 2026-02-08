package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.util.ProjectHelpers;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;

@Slf4j
@Controller
public class OffersSearchController {
  private final OfferService offerService;
  private final BrandService brandService;
  private final ModelMapper modelMapper;
  private final ModelService modelService;

  public OffersSearchController(OfferService offerService, BrandService brandService, ModelMapper modelMapper, ModelService modelService) {
    this.offerService = offerService;
    this.brandService = brandService;
    this.modelMapper = modelMapper;
    this.modelService = modelService;
  }

  // 1. GET offers/find -> offers/find/vehicleType
  @GetMapping("/offers/find")
  public String getFindOffersView() {
    return "offers-categories";
  }

  // 2. GET offers/find/vehicleType -> POST offers/find/vehicleType
  @GetMapping("/offers/find/{vehicleType}")
  public String getSearchFormByCategory(@PathVariable String vehicleType,
                                        Model model) {

    model.addAttribute("vehicleType", VehicleCategoryEnum.from(vehicleType));
    model.addAttribute("brands", this.brandService.findAllBrands());

    return "offers-find";
  }

  // 3. POST-REDIRECT-GET - offers/find/vehicleType -> offers/{vehicleType}/{brand}/{modelName}")
  @PostMapping("/offers/find/{vehicleType}")
  public String submitFindOffersForm(
          @PathVariable String vehicleType,
          @Valid OffersFindBindingModel offersFindBindingModel,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes) {

    VehicleCategoryEnum vehicleCategoryEnum = VehicleCategoryEnum.from(vehicleType);

    // ignore city field (fetched with AJAX)
    boolean hasRelevantErrors = bindingResult
            .getFieldErrors()
            .stream()
            .anyMatch(e -> !"city".equals(e.getField()));

    if (hasRelevantErrors) {
      redirectAttributes
              .addFlashAttribute("offersFindBindingModel", offersFindBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.offersFindBindingModel", bindingResult)
              .addFlashAttribute("models", offersFindBindingModel.getBrand() == null ? "" : this.modelService.findModelsByVehicleTypeAndBrand(offersFindBindingModel.getBrand(), vehicleCategoryEnum));

      return "redirect:/offers/find/" + vehicleType;
    }

    redirectAttributes.addFlashAttribute("filters", offersFindBindingModel);

    return "redirect:/offers/" + vehicleType + "/" + offersFindBindingModel.getBrand().toLowerCase(Locale.ROOT) + "/" + offersFindBindingModel.getModel().toLowerCase(Locale.ROOT);
  }


  // 4. GET - Show filtered offers
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
      categoryEnum = VehicleCategoryEnum.from(vehicleType);
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

  // II. Quick search ->
  @PostMapping("/offers/quick-search")
  public String submitQuickSearch(
          @Valid @ModelAttribute("offersFindBindingModel") OffersFindBindingModel form,
          BindingResult bindingResult,
          @RequestParam String vehicleType,
          RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("offersFindBindingModel", form);
      redirectAttributes.addFlashAttribute(
              "org.springframework.validation.BindingResult.offersFindBindingModel",
              bindingResult);
      return "redirect:/";
    }

    redirectAttributes.addFlashAttribute("filters", form);

    return "redirect:/offers/" + vehicleType.toLowerCase()
            + "/" + form.getBrand().toLowerCase()
            + "/" + form.getModel().toLowerCase();
  }

  // II. Find offers - By brands
  @GetMapping("/offers/brands/{brand}")
  public String getOffersByBrand(
          @PathVariable String brand,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size,
          Model model) {

    String sortField = switch (sort) {
      case "price" -> "price";
      case "mileage" -> "mileage";
      default -> "created";
    };

    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    Pageable pageable = PageRequest.of(page, size, sorting);

    Page<OfferBaseViewModel> offersPage =
            offerService.findOffersByBrand(
                    brand.toUpperCase(Locale.ROOT),
                    pageable);

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("brand", ProjectHelpers.capitalizeString(brand));
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("context", "brand");

    return "offers";
  }
}
