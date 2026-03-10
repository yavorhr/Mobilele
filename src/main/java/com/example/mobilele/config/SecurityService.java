package com.example.mobilele.config;

import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Component;

@Component("security")
public class SecurityService {

  private final UserService userService;
  private final OfferService offerService;

  public SecurityService(UserService userService, OfferService offerService) {
    this.userService = userService;
    this.offerService = offerService;
  }


  public boolean canModifyOffer(String username, Long offerId) {
    return userService.isOwnerOrIsAdmin(username, offerId);
  }

  public boolean canModifyUser(String currentUsername, String targetUsername) {
    return !currentUsername.equals(targetUsername);
  }

  public boolean isNotModifyingOwnProfile(String targetUsername, String currentUsername) {
    return userService.isNotModifyingOwnProfile(targetUsername, currentUsername);
  }

  public boolean canToggleFavorite(String username, Long offerId) {
    return userService.isNotOwnerOrIsAdmin(username, offerId);
  }
}