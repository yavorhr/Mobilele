package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.UserLoginBindingModel;
import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {
  private final UserService userService;
  private final CurrentUser currentUser;

  public UserLoginController(UserService userService, CurrentUser currentUser) {
    this.userService = userService;
    this.currentUser = currentUser;
  }

  @GetMapping("/users/login")
  public String loginPage() {
    return "auth-login";
  }

  @PostMapping("/users/login")
  public String registerUser(@ModelAttribute UserLoginBindingModel userLoginBindingModel) {
    UserLoginServiceModel userLoginServiceModel = mapToServiceModel(userLoginBindingModel);

    boolean loginSuccessful = this.userService.login(userLoginServiceModel);

    if (loginSuccessful) {
      return "redirect:/";
    }

    return "auth-login";
  }

  @GetMapping("/users/logout")
  public String logout() {
    this.currentUser.clean();
    return "redirect:/";
  }

  // helpers

  private UserLoginServiceModel mapToServiceModel(UserLoginBindingModel userLoginBindingModel) {
    UserLoginServiceModel userLoginServiceModel = new UserLoginServiceModel();
    userLoginServiceModel.setUsername(userLoginBindingModel.getUsername());
    userLoginServiceModel.setPassword(userLoginBindingModel.getPassword());
    return userLoginServiceModel;
  }
}
