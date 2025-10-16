package com.example.mobilele.model.service.offer;

public class PictureAddServiceModel {
  private String title;
  private Long offerId;
  private Long userId;
  private String publicId;
  private String url;

  public Long getUserId() {
    return userId;
  }

  public String getPublicId() {
    return publicId;
  }

  public String getTitle() {
    return title;
  }

  public PictureAddServiceModel setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Long getOfferId() {
    return offerId;
  }

  public PictureAddServiceModel setOfferId(Long offerId) {
    this.offerId = offerId;
    return this;
  }

  public PictureAddServiceModel setUserId(Long userId) {
    this.userId = userId;
    return this;
  }

  public PictureAddServiceModel setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }

  public PictureAddServiceModel setUrl(String url) {
    this.url = url;
    return this;
  }
}
