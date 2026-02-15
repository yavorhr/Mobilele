package com.example.mobilele.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Component
public class MultipartFileListValidator implements ConstraintValidator<NotEmptyFiles, List<MultipartFile>> {

  @Override
  public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
    if (files == null || files.isEmpty()) {
      return false;
    }

    return files.stream().anyMatch(file -> file != null && !file.isEmpty());
  }
}

