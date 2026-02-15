package com.example.mobilele.web;

import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.util.Constants;
import com.example.mobilele.util.ProjectHelpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Locale;

@Slf4j
@Controller
public class OffersUserController {
  private final OfferService offerService;
  private final MessageSource messageSource;

  public OffersUserController(OfferService offerService, MessageSource messageSource) {
    this.offerService = offerService;
    this.messageSource = messageSource;
  }

  @GetMapping("/offers/my-offers")
  public String showMyOffers(
          Principal principal,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "8") int size,
          Model model,
          Locale locale) {

    String username = principal.getName();

    Pageable pageable = ProjectHelpers.create(sort, dir, page, size);

    Page<OfferBaseViewModel> offersPage = offerService.findOffersByUserId(username, pageable);

    String title = ProjectHelpers.resolveLocalizedTitle(
            Constants.CONTEXT_MY,
            null,
            null,
            messageSource,
            locale
    );

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("title", title);

    return "offers";
  }

  @GetMapping("/offers/favorites")
  public String showFavoriteOffers(
          Principal principal,
          @RequestParam(defaultValue = "creationDate") String sort,
          @RequestParam(defaultValue = "desc") String dir,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "1") int size,
          Model model, Locale locale) {

    String username = principal.getName();

    Pageable pageable = ProjectHelpers.create(sort, dir, page, size);

    Page<OfferBaseViewModel> offersPage = offerService.findFavoriteOffers(username, pageable);

    String title = ProjectHelpers.resolveLocalizedTitle(
            Constants.CONTEXT_FAVORITES,
            null,
            null,
            messageSource,
            locale
    );

    model.addAttribute("offers", offersPage.getContent());
    model.addAttribute("sort", sort);
    model.addAttribute("dir", dir);
    model.addAttribute("currentPage", offersPage.getNumber());
    model.addAttribute("totalPages", offersPage.getTotalPages());
    model.addAttribute("title", title);

    return "offers";
  }
}
