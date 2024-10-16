package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity {
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private Integer year;
  private ModelEntity model;
  private UserEntity seller;

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  @Enumerated
  @Column(nullable = false)
  public EngineEnum getEngine() {
    return engine;
  }

  @Column(name = "image_url", nullable = false)
  public String getImageUrl() {
    return imageUrl;
  }

  @Column(name = "mileage", nullable = false)
  public Double getMileage() {
    return mileage;
  }

  @Column(name = "price", nullable = false)
  public Double getPrice() {
    return price;
  }

  @Enumerated
  @Column(nullable = false)
  public TransmissionType getTransmission() {
    return transmission;
  }

  @Column(nullable = false)
  public Integer getYear() {
    return year;
  }

  @ManyToOne
  public ModelEntity getModel() {
    return model;
  }

  @ManyToOne
  public UserEntity getSeller() {
    return seller;
  }

  public OfferEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferEntity setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferEntity setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public OfferEntity setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferEntity setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferEntity setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferEntity setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferEntity setModel(ModelEntity model) {
    this.model = model;
    return this;
  }

  public OfferEntity setSeller(UserEntity seller) {
    this.seller = seller;
    return this;
  }
}
