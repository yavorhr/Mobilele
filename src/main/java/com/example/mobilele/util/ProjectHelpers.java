package com.example.mobilele.util;


import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Locale;

public final class ProjectHelpers {

  private ProjectHelpers() {
  }


  public static String convertPictureTitle(String originalName) {
    if (originalName != null) {
      int dotIndex = originalName.lastIndexOf('.');

      if (dotIndex > 0) {
        originalName = originalName.substring(0, dotIndex);
      }
    }
    return originalName;
  }

  public static String capitalizeString(String initStr) {
    return initStr.substring(0, 1).toUpperCase() + initStr.substring(1);
  }

  // Pagination
  public static Pageable create(String sort, String dir, int page, int size) {
    String sortField = switch (sort) {
      case "price" -> "price";
      case "mileage" -> "mileage";
      default -> "created";
    };

    Sort sorting = Sort.by(Sort.Direction.fromString(dir), sortField);
    return PageRequest.of(page, size, sorting);
  }

  // Context for dynamic header
  public record TitleContext(String key, Object[] args) {
  }

  public static TitleContext resolveTitle(String context, String brand, String model) {
    return switch (context) {
      case "model" -> new TitleContext("offers.title.model", new Object[]{ProjectHelpers.capitalizeString(brand) , ProjectHelpers.capitalizeString(model)});
      case "brand" -> new TitleContext("offers.title.brand", new Object[]{ProjectHelpers.capitalizeString(brand)});
      case "favorites" -> new TitleContext("offers.title.favorites", new Object[]{});
      case "my" -> new TitleContext("offers.title.my", new Object[]{});
      default -> new TitleContext("offers.title.all", new Object[]{});
    };
  }

  public static String resolveLocalizedTitle(
          String context,
          String brand,
          String model,
          MessageSource messageSource,
          Locale locale) {

    TitleContext ctx = resolveTitle(context, brand, model);

    return messageSource.getMessage(
            ctx.key(),
            ctx.args(),
            locale
    );
  }
}
