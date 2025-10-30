package com.example.mobilele.validator;

import com.example.mobilele.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
  private final UserService userService;

  public PhoneNumberValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      return true;
    }

    return userService.isPhoneNumberAvailable(phoneNumber);
  }
}
