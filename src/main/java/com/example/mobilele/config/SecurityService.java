package com.example.mobilele.config;

import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Component;

@Component("security")
public class SecurityService {

  private final UserService userService;

  public SecurityService(UserService userService) {
    this.userService = userService;
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
}