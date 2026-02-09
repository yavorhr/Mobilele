package com.example.mobilele.util;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

  public static String capitalizeString(String initStr){
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
  public static String resolveTitle(String context, String brand, String model) {
    return switch (context) {
      case "model" -> "All offers for " + brand + " " + model;
      case "brand" -> "All offers for " + brand;
      case "favorites" -> "Favorite offers";
      case "my" -> "My offers";
      default -> "All offers";
    };
  }
}
