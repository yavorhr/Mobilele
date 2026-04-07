package com.example.mobilele.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
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

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    return ResponseEntity
            .badRequest()
            .body("Invalid value for parameter: " + ex.getName());
  }
}


