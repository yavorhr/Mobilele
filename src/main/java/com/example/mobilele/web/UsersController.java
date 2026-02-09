package com.example.mobilele.web;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.binding.user.UserRegisterBindingModel;
import com.example.mobilele.model.entity.enums.LoginErrorType;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UsersController {
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final FeedbackService feedbackService;

  public UsersController(UserService userService, ModelMapper modelMapper, FeedbackService feedbackService) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.feedbackService = feedbackService;
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

  @PreAuthorize("@userServiceImpl.isNotOwnerOrIsAdmin(#principal.name,#offerId)")
  @PostMapping("/favorites/{offerId}/toggle")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> toggleFavorite(
          @PathVariable Long offerId,
          Principal principal) {

    Map<String, Object> response = new HashMap<>();

    try {
      boolean added = userService.toggleFavorite(principal.getName(), offerId);

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

  @PostMapping("/submit-feedback")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> submitFeedback(
          @RequestParam int rating,
          @RequestParam String comment,
          @AuthenticationPrincipal MobileleUser principal) {

    Map<String, Object> response = new HashMap<>();

    // Rating validation
    if (rating < 1) {
      response.put("success", false);
      response.put("message", "Please select at least one star.");
      return ResponseEntity.badRequest().body(response);
    }

    // Comment validation
    if (comment == null || comment.trim().length() < 5) {
      response.put("success", false);
      response.put("message", "Comment must be at least 5 characters long.");
      return ResponseEntity.badRequest().body(response);
    }

    try {
      feedbackService.leaveFeedback(principal.getUsername(), rating, comment);
      response.put("success", true);
      response.put("message", "Thank you for your feedback!");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.put("success", false);
      response.put("message", e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }
}
