package com.example.mobilele.web.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.AccessDeniedException;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionsHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ModelAndView handleObjectNotFoundException(ObjectNotFoundException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/404");
    modelAndView.setStatus(HttpStatus.NOT_FOUND);

    return modelAndView;
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleServerError(Exception e) {
    ModelAndView modelAndView = new ModelAndView();

    if (e instanceof AccessDeniedException
            || e instanceof org.springframework.security.authorization.AuthorizationDeniedException) {
      modelAndView.addObject("message", e.getMessage());
      modelAndView.setViewName("error/403");
      modelAndView.setStatus(HttpStatus.FORBIDDEN);
    } else {
      modelAndView.setViewName("error/500");
      modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return modelAndView;
  }

  @ExceptionHandler(DuplicatePhoneException.class)
  @ResponseBody
  public ResponseEntity<Map<String, String>> handleDuplicatePhone(DuplicatePhoneException ex) {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(Map.of("phoneNumber", ex.getMessage()));
  }

}
