package com.example.mobilele.web;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.binding.user.UserRegisterBindingModel;
import com.example.mobilele.model.entity.enums.LoginErrorType;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UsersController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UsersController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @ModelAttribute("userRegisterBindingModel")
  public UserRegisterBindingModel userModel() {
    return new UserRegisterBindingModel();
  }

  //1. Register
  @GetMapping("/register")
  public String registerPage(Model model) {
    System.out.println(model);
    return "register";
  }

  @PostMapping("/register")
  public String register(
          @Valid UserRegisterBindingModel userRegisterBindingModel,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

      return "redirect:register";
    }

    UserRegisterServiceModel serviceModel =
            modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);

    userService.registerAndLoginUser(serviceModel);

    return "redirect:/";
  }

  //2. Login
  @GetMapping("/login")
  public String login(@RequestParam(value = "errorType", required = false) String errorType, Model model) {

    if (errorType != null) {
      try {
        LoginErrorType type = LoginErrorType.valueOf(errorType);

        switch (type) {
          case INVALID_CREDENTIALS -> model.addAttribute("login_error_message", "Invalid username or password");
          case USER_NOT_FOUND -> model.addAttribute("login_error_message", "User with this username does not exist");
          case ACCOUNT_LOCKED -> model.addAttribute("login_error_message", "Your account is locked. Try again in 15 minutes");
        }
      } catch (IllegalArgumentException e) {
        model.addAttribute("login_error_message", "An unknown error occurred.");
      }
    }
    return "login";
  }

  //3. Profile
  @GetMapping("/profile")
  public String showProfile(Model model,
                            @AuthenticationPrincipal MobileleUser principal) {

    model.addAttribute("user", this.userService.findUserViewModelById(principal.getId()));

    return "profile";
  }

  @PatchMapping("/profile")
  @ResponseBody
  public ResponseEntity<UserViewModel> updateProfile(@RequestBody UserEditBindingModel bindingModel,
                                                     @AuthenticationPrincipal MobileleUser principal) {

    Long userId = principal.getId();
    UserViewModel updatedUser = userService.updateUserProfile(userId, bindingModel);

    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/delete")
  public String deleteUserProfile(
          @AuthenticationPrincipal MobileleUser principal,
          HttpServletRequest request,
          HttpServletResponse response) throws ServletException {

    Long userId = principal.getId();
    this.userService.deleteProfileById(userId);

    request.logout();

    return "redirect:/";
  }
}
