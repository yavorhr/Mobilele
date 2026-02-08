package com.example.mobilele.web;

import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Slf4j
@Controller
public class OffersUserController {
  private final OfferService offerService;

  public OffersUserController(OfferService offerService) {
    this.offerService = offerService;
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
}
