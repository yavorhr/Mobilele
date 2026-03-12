package com.example.mobilele.config;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.UserSecurityService;
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
  private final UserSecurityService userSecurityService;

  public CustomLoginSuccessHandler(UserService userService, UserSecurityService userSecurityService) {
    this.userService = userService;
    this.userSecurityService = userSecurityService;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String username = authentication.getName();
    UserEntity user = userService.findByUsername(username);

    if (user != null) {
      userSecurityService.resetFailedAttempts(user);
    }

    response.sendRedirect("/");
  }
}
