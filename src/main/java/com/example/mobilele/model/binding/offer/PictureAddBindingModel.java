package com.example.mobilele.model.binding.offer;

import org.springframework.web.multipart.MultipartFile;

public class PictureAddBindingModel {
  private String title;
  private MultipartFile picture;
  private Long offerId;

  public String getTitle() {
    return title;
  }

  public PictureAddBindingModel setTitle(String title) {
    this.title = title;
    return this;
  }

  public Long getOfferId() {
    return offerId;
  }
  public MultipartFile getPicture() {
    return picture;
  }

  public PictureAddBindingModel setPicture(MultipartFile picture) {
    this.picture = picture;
    return this;
  }

  public PictureAddBindingModel setOfferId(Long offerId) {
    this.offerId = offerId;
    return this;
  }
}
