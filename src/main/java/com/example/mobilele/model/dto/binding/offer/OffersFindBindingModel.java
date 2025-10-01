package com.example.mobilele.model.dto.binding.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

public class OffersFindBindingModel {
  private EngineEnum engine;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private String model;
  private String brand;
  private Integer year;

  public OffersFindBindingModel() {

  }

  public EngineEnum getEngine() {
    return engine;
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

  public String getModel() {
    return model;
  }

  public String getBrand() {
    return brand;
  }

  public Integer getYear() {
    return year;
  }

  public OffersFindBindingModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OffersFindBindingModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OffersFindBindingModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OffersFindBindingModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OffersFindBindingModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OffersFindBindingModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public OffersFindBindingModel setYear(Integer year) {
    this.year = year;
    return this;
  }
}
