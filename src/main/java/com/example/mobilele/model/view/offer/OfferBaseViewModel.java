package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

public class OfferBaseViewModel {
  private Long id;
  private Double price;
  private String modelName;
  private String modelBrandName;
  private TransmissionType transmission;
  private EngineEnum engine;
  private Double mileage;
  private String profileImage;
  private String modelYear;

  public OfferBaseViewModel() {
  }

  public String getModelYear() {
    return modelYear;
  }

  public Long getId() {
    return id;
  }

  public Double getPrice() {
    return price;
  }

  public String getModelName() {
    return modelName;
  }

  public String getModelBrandName() {
    return modelBrandName;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public Double getMileage() {
    return mileage;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public OfferBaseViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferBaseViewModel setModelYear(String modelYear) {
    this.modelYear = modelYear;
    return this;
  }

  public OfferBaseViewModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferBaseViewModel setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public OfferBaseViewModel setModelBrandName(String modelBrandName) {
    this.modelBrandName = modelBrandName;
    return this;
  }

  public OfferBaseViewModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferBaseViewModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferBaseViewModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferBaseViewModel setProfileImage(String profileImage) {
    this.profileImage = profileImage;
    return this;
  }
}
