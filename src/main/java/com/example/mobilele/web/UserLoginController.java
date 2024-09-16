package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.UserLoginBindingModel;
import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserLoginController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserLoginController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/users/login")
  public String loginPage() {
    return "auth-login";
  }

  @PostMapping("/users/login")
  public String registerUser(@ModelAttribute UserLoginBindingModel userLoginBindingModel) {
    UserLoginServiceModel userLoginServiceModel = mapToServiceModel(userLoginBindingModel);

    boolean isLoginSuccessful = this.userService.login(userLoginServiceModel);

    if (isLoginSuccessful) {
      return "redirect:/";
    }

    return "auth-login";
  }

  // helpers

  private UserLoginServiceModel mapToServiceModel(UserLoginBindingModel userLoginBindingModel) {
    UserLoginServiceModel userLoginServiceModel = new UserLoginServiceModel();
    userLoginServiceModel.setUsername(userLoginBindingModel.getUsername());
    userLoginServiceModel.setPassword(userLoginBindingModel.getPassword());
    return userLoginServiceModel;
  }
}
