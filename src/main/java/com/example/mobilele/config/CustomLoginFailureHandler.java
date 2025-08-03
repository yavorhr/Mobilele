package com.example.mobilele.config;

import com.example.mobilele.model.entity.enums.LoginErrorType;
import com.example.mobilele.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
  private final UserService userService;

  public CustomLoginFailureHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    String username = request.getParameter("username");
    String errorType = LoginErrorType.INVALID_CREDENTIALS.name();

    try {
      userService.findByUsername(username);
    } catch (ObjectNotFoundException e) {
      errorType = LoginErrorType.USER_NOT_FOUND.name();
    }
    response.sendRedirect("/users/login?errorType=" + errorType);
  }
}
