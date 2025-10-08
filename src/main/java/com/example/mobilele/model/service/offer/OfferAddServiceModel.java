package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

public class OfferAddServiceModel {

  private Long id;
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private Double price;
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

  public Double getPrice() {
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

  public OfferAddServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferAddServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferAddServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferAddServiceModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public OfferAddServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferAddServiceModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferAddServiceModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferAddServiceModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferAddServiceModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OfferAddServiceModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }
}
