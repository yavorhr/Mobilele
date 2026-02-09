package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OfferUpdateBindingForm;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  // 1. Get offers
  @GetMapping("/offers/all")
  public String showAllOffers(
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
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

    return "offers";
  }

  // Get top 20 offers
  @GetMapping("/offers/top-offers")
  public String showTop20ByViewsCount(Model model) {
    List<OfferBaseViewModel> topOffers = offerService.findTopOffersByViews();

    model.addAttribute("offers", topOffers);

    return "top-offers";
  }

  // 2. Offer Details
  @GetMapping("/offers/details/{id}")
  public String getOffersDetailsPage(@PathVariable Long id, Model model,
                                     HttpServletRequest request,
                                     Principal principal) {

    offerService.incrementViewsIfEligible(id, request, principal);

    OfferViewModel viewModel =
            this.modelMapper.map(this.offerService
                            .findOfferById(principal.getName(), id),
                    OfferViewModel.class);

    model.addAttribute("offer", viewModel);
    model.addAttribute("isFavorite", this.offerService.doesOfferExistInUsersFavorites(id, principal.getName()));

    return "details";
  }

  // 3. Add Offer
  @GetMapping("/offers")
  public String getAddOffersPage(Model model) {
    model.addAttribute("brands", this.brandService.findAllBrands());
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

  // 4. Update Offer
  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id )")
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

  @GetMapping("/offers/update/errors/{id}")
  public String editOfferErrors(@PathVariable Long id) {
    return "update";
  }

  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id)")
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

  // 5. Delete offer
  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id)")
  @DeleteMapping("/offers/{id}")
  public String deleteOffer(@AuthenticationPrincipal MobileleUser principal,
                            @PathVariable Long id,
                            RedirectAttributes redirectAttributes,
                            boolean soldOffer) {

    if (soldOffer) {
      offerService.saveSoldOffer(id);
      offerService.deleteById(id);
      redirectAttributes.addFlashAttribute("flashMessage",
              "✅ Offer deleted and marked as sold via Mobilele. Thank you!");
    } else {
      offerService.deleteById(id);
      redirectAttributes.addFlashAttribute("flashMessage",
              "✅ Offer deleted successfully!");
    }

    redirectAttributes.addFlashAttribute("flashType", "success");

    return "redirect:/";
  }
}


