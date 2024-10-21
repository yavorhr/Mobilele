package com.example.mobilele.validator.validator;

import com.example.mobilele.model.dto.binding.user.UserLoginBindingModel;
import com.example.mobilele.model.dto.service.user.UserLoginServiceModel;
import com.example.mobilele.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.modelmapper.ModelMapper;

public class LoginValidator implements ConstraintValidator<ValidLogin, UserLoginBindingModel> {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public LoginValidator(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean isValid(UserLoginBindingModel bindingModel, ConstraintValidatorContext context) {
    boolean isLoginSuccessful = this.userService.login(modelMapper.map(bindingModel, UserLoginServiceModel.class));

    if (!isLoginSuccessful) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Invalid username or password.")
              .addConstraintViolation();
    }

    return  isLoginSuccessful;
  }
}
