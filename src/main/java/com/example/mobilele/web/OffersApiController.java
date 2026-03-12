package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.CityEnum;
import com.example.mobilele.model.entity.enums.CountryEnum;
import com.example.mobilele.model.view.CityResponse;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class OffersApiController {
  private final MessageSource messageSource;
  private final FavoritesService favoritesService;

  public OffersApiController(MessageSource messageSource, FavoritesService favoritesService) {
    this.messageSource = messageSource;
    this.favoritesService = favoritesService;
  }

  @GetMapping("/locations/cities")
  public List<CityResponse> getCities(@RequestParam CountryEnum country, Locale locale) {

    return Arrays.stream(CityEnum.values())
            .filter(city -> city.getCountry() == country)
            .map(city -> new CityResponse(
                    city.name(),
                    messageSource.getMessage(
                            "city." + city.name(),
                            null,
                            locale)
            ))
            .toList();
  }

  // Offer Reservation
  @PreAuthorize("@security.canModifyOffer(#principal.username, #id )")
  @PatchMapping("/offers/{id}/toggle-reservation")
  public ResponseEntity<Map<String, Boolean>> toggleReservation(@PathVariable Long id,
                                                                @AuthenticationPrincipal MobileleUser principal) {

    boolean newStatus = favoritesService.toggleReservation(id, principal.getUsername());
    return ResponseEntity.ok(Map.of("reserved", newStatus));
  }
}
