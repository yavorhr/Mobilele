package com.example.mobilele.config;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
  private final UserService userService;

  public CustomLoginSuccessHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String username = authentication.getName();
    UserEntity user = userService.findByUsername(username);

    if (user != null) {
      userService.resetFailedAttempts(user);
    }

    response.sendRedirect("/");
  }
}
