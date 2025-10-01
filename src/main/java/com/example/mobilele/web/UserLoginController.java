package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.LoginErrorType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserLoginController {

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
}
