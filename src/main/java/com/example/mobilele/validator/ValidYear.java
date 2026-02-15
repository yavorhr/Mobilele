package com.example.mobilele.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {
  String message() default "{validation.year.period}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
