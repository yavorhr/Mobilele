package com.example.mobilele.model.service.offer;

public class OfferServiceModelWithIdAndUrl {
  private Long id;
  private String imageUrl;

  public OfferServiceModelWithIdAndUrl() {
  }

  public Long getId() {
    return id;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public OfferServiceModelWithIdAndUrl setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferServiceModelWithIdAndUrl setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }
}
