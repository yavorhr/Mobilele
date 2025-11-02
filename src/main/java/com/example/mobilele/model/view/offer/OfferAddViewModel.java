package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

import java.math.BigDecimal;

public class OfferAddViewModel {

  private Long id;
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private BigDecimal price;
  private TransmissionType transmission;
  private Integer year;
  private String model;
  private String brand;

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Double getMileage() {
    return mileage;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  public Integer getYear() {
    return year;
  }

  public String getModel() {
    return model;
  }

  public String getBrand() {
    return brand;
  }

  public OfferAddViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferAddViewModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferAddViewModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferAddViewModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public OfferAddViewModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferAddViewModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public OfferAddViewModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferAddViewModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferAddViewModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OfferAddViewModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }
}
