package com.example.mobilele.config;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.enums.LoginErrorType;
import com.example.mobilele.service.UserService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
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
    String errorType = LoginErrorType.INVALID_CREDENTIALS.name();
    String username = request.getParameter("username");

    try {
      UserEntity user = userService.findByUsername(username);

      if (exception instanceof LockedException) {
        errorType = LoginErrorType.ACCOUNT_LOCKED.name();
      }

      userService.increaseUserFailedLoginAttempts(user);

    } catch (ObjectNotFoundException e) {
      errorType = LoginErrorType.USER_NOT_FOUND.name();
    }

    response.sendRedirect("/users/login?errorType=" + errorType);
  }
}
