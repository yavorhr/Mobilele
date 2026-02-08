package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.CityEnum;
import com.example.mobilele.model.entity.enums.CountryEnum;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class OffersApiController {
  private final OfferService offerService;

  public OffersApiController(OfferService offerService) {
    this.offerService = offerService;
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

  // Offer Reservation
  @PreAuthorize("@userServiceImpl.isOwnerOrIsAdmin(#principal.username, #id)")
  @PatchMapping("/offers/{id}/toggle-reservation")
  @ResponseBody
  public ResponseEntity<Map<String, Boolean>> toggleReservation(@PathVariable Long id,
                                                                @AuthenticationPrincipal MobileleUser principal) {

    boolean newStatus = offerService.toggleReservation(id, principal.getUsername());
    return ResponseEntity.ok(Map.of("reserved", newStatus));
  }
}
