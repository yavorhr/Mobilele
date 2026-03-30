package com.example.mobilele.web;

import com.example.mobilele.service.FavoritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class FavoritesController {
  private final FavoritesService favoritesService;

  public FavoritesController(FavoritesService favoritesService) {
    this.favoritesService = favoritesService;
  }

  @PreAuthorize("@security.canToggleFavorite(#principal.name,#offerId)")
  @PostMapping("/favorites/{offerId}/toggle")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> toggleFavorite(
          @PathVariable Long offerId,
          Principal principal) {

    Map<String, Object> response = new HashMap<>();

    try {
      boolean added = favoritesService.toggleFavorite(principal.getName(), offerId);

      response.put("success", true);
      response.put("message", added
              ? "Offer added"
              : "Offer removed");

      return ResponseEntity.ok(response);

    } catch (RuntimeException e) {
      response.put("success", false);
      response.put("message", e.getMessage() != null
              ? e.getMessage()
              : "Unexpected error occurred.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}
