package com.example.mobilele.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidYear, Integer> {

  private static final int MIN_YEAR = 1900;

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    int currentYear = Year.now().getValue();
    return value >= MIN_YEAR && value <= currentYear;
  }
}
