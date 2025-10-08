package com.example.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "offers_pictures")
public class Picture extends BaseEntity {
  private String title;
  private String url;
  private UserEntity seller;
  private OfferEntity offer;
  private String publicId;

  public Picture() {
  }

  public String getPublicId() {
    return publicId;
  }

  @ManyToOne
  public UserEntity getSeller() {
    return seller;
  }

  @ManyToOne
  public OfferEntity getOffer() {
    return offer;
  }

  @Column()
  public String getTitle() {
    return title;
  }

  @Column()
  public String getUrl() {
    return url;
  }

  public Picture setTitle(String title) {
    this.title = title;
    return this;
  }

  public Picture setUrl(String url) {
    this.url = url;
    return this;
  }

  public Picture setSeller(UserEntity seller) {
    this.seller = seller;
    return this;
  }

  public Picture setOffer(OfferEntity offer) {
    this.offer = offer;
    return this;
  }

  public Picture setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }
}
