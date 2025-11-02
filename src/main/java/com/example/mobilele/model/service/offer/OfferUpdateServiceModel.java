package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;

import java.math.BigDecimal;

public class OfferUpdateServiceModel {
  private Long id;
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private BigDecimal price;
  private TransmissionType transmission;
  private Integer year;
  private String model;

  public OfferUpdateServiceModel() {
  }

  public Long getId() {
    return id;
  }

  public OfferUpdateServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public OfferUpdateServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public OfferUpdateServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public OfferUpdateServiceModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public Double getMileage() {
    return mileage;
  }

  public OfferUpdateServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public OfferUpdateServiceModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  public OfferUpdateServiceModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public Integer getYear() {
    return year;
  }

  public OfferUpdateServiceModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public String getModel() {
    return model;
  }

  public OfferUpdateServiceModel setModel(String model) {
    this.model = model;
    return this;
  }
}
