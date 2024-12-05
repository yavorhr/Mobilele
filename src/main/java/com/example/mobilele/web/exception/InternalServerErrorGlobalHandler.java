package com.example.mobilele.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class InternalServerErrorGlobalHandler {

  @ExceptionHandler
  public ModelAndView handleNullPointerExceptions(NullPointerException e) {
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.addObject("message", e.getMessage());
    modelAndView.setViewName("error/500");
    modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

    return modelAndView;
  }
}
