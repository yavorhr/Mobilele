package com.example.mobilele.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
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
