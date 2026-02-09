package com.example.mobilele.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface UniquePhoneNumber {

  String message() default "Phone number already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
