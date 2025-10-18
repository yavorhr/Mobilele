package com.example.mobilele.model.service.user;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class PicturesAddServiceModel {
  private List<MultipartFile> pictures;
  private Long offerId;
  private Long userId;

  public PicturesAddServiceModel() {
  }

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  public Long getOfferId() {
    return offerId;
  }

  public Long getUserId() {
    return userId;
  }

  public PicturesAddServiceModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public PicturesAddServiceModel setOfferId(Long offerId) {
    this.offerId = offerId;
    return this;
  }

  public PicturesAddServiceModel setUserId(Long userId) {
    this.userId = userId;
    return this;
  }
}
