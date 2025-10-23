package com.example.mobilele.util;


public final class ProjectHelpers {

  private ProjectHelpers() {
  }

  // Private & helpers
  public static String convertPictureTitle(String originalName) {
    if (originalName != null) {
      int dotIndex = originalName.lastIndexOf('.');

      if (dotIndex > 0) {
        originalName = originalName.substring(0, dotIndex);
      }
    }
    return originalName;
  }
}
