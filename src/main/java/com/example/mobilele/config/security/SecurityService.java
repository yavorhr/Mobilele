package com.example.mobilele.config.security;

import com.example.mobilele.service.UserAdminService;
import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Component;

@Component("security")
public class SecurityService {

  private final UserService userService;
  private final UserAdminService userAdminService;

  public SecurityService(UserService userService, UserAdminService userAdminService) {
    this.userService = userService;
    this.userAdminService = userAdminService;
  }

  public boolean canModifyOffer(String username, Long offerId) {
    return userService.isOwnerOrIsAdmin(username, offerId);
  }

  public boolean isNotModifyingOwnProfile(String targetUsername, String currentUsername) {
    return userAdminService.isNotModifyingOwnProfile(targetUsername, currentUsername);
  }

  public boolean canToggleFavorite(String username, Long offerId) {
    return userService.isNotOwnerOrIsAdmin(username, offerId);
  }
}