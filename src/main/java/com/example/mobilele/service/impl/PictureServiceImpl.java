package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

  private final CloudinaryService cloudinaryService;

  public PictureServiceImpl(CloudinaryService cloudinaryService) {
    this.cloudinaryService = cloudinaryService;
  }

  @Override
  public List<Picture> addOfferPictures(List<MultipartFile> pictures) throws IOException {
    List<Picture> uploadedPictures = new ArrayList<>();

    for (MultipartFile file : pictures) {
      CloudinaryImage uploaded = cloudinaryService.upload(file, "cars-offers");

      Picture picture = new Picture();
      picture.setUrl(uploaded.getUrl());
      picture.setPublicId(uploaded.getPublicId());
      picture.setTitle(convertTitle(file.getOriginalFilename()));
    }

    return uploadedPictures;
  }

  // Helpers
  private String convertTitle(String originalName) {
    if (originalName != null) {
      int dotIndex = originalName.lastIndexOf('.');

      if (dotIndex > 0) {
        originalName = originalName.substring(0, dotIndex);
      }
    }
    return originalName;
  }
}
