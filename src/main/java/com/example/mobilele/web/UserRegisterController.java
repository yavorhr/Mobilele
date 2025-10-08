package com.example.mobilele.web;

import com.example.mobilele.model.binding.user.UserRegisterBindingModel;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
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
@RequestMapping("/users")
public class UserRegisterController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserRegisterController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @ModelAttribute("userRegisterBindingModel")
  public UserRegisterBindingModel userModel() {
    return new UserRegisterBindingModel();
  }

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
}
