package com.example.mobilele.model.service;

public class PictureServiceModel {
  private String pictureUrl;
  private String picturePublicId;

  public PictureServiceModel() {
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public String getPicturePublicId() {
    return picturePublicId;
  }

  public PictureServiceModel setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }

  public PictureServiceModel setPicturePublicId(String picturePublicId) {
    this.picturePublicId = picturePublicId;
    return this;
  }

  public boolean canBeDeleted() {
    return picturePublicId != null && picturePublicId.startsWith("mobilele/api/");
  }
}

