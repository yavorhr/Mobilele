package com.example.mobilele.web;

import com.example.mobilele.model.dto.binding.user.UserLoginBindingModel;
import com.example.mobilele.model.dto.binding.user.UserRegisterBindingModel;
import com.example.mobilele.model.dto.service.user.UserLoginServiceModel;
import com.example.mobilele.model.dto.service.user.UserRegisterServiceModel;
import com.example.mobilele.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users/")
public class UsersController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UsersController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @ModelAttribute("userModel")
  public UserRegisterBindingModel userModel() {
    return new UserRegisterBindingModel();
  }

  @GetMapping("/register")
  public String registerPage() {
    return "auth-register";
  }


  @PostMapping("/register")
  public String register(
          @Valid UserRegisterBindingModel userModel,
          BindingResult bindingResult,
          RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors() || !userModel.getPassword().equals(userModel.getConfirmPassword())) {
      redirectAttributes.addFlashAttribute("userModel", userModel);
      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

      return "redirect:/users/register";
    }

    UserRegisterServiceModel serviceModel =
            modelMapper.map(userModel, UserRegisterServiceModel.class);

    userService.registerAndLoginUser(serviceModel);

    return "redirect:/users/login";
  }

  @GetMapping("/login")
  public String loginPage(Model model) {
    if (!model.containsAttribute("userLoginBindingModel")) {
      model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
    }

    return "auth-login";
  }

  @PostMapping("/login")
  public String loginUser(
          @Valid UserLoginBindingModel userLoginBindingModel,
          BindingResult bindingResult,
          RedirectAttributes attributes) {

    if (bindingResult.hasErrors()) {

      attributes
              .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

      return "redirect:/users/login";
    }

    return "redirect:/";
  }

}
