package com.example.mobilele.model.dto.view.offer;

public class OfferViewModelWithIdAndUrl {
  private Long id;
  private String imageUrl;

  public OfferViewModelWithIdAndUrl() {
  }

  public Long getId() {
    return id;
  }

  public OfferViewModelWithIdAndUrl setId(Long id) {
    this.id = id;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public OfferViewModelWithIdAndUrl setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }
}
