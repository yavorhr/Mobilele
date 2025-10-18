package com.example.mobilele.model.binding.offer;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class PicturesAddBindingModel {
  private List<MultipartFile> pictures;
  private Long offerId;

  public Long getOfferId() {
    return offerId;
  }

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  public PicturesAddBindingModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public PicturesAddBindingModel setOfferId(Long offerId) {
    this.offerId = offerId;
    return this;
  }
}
