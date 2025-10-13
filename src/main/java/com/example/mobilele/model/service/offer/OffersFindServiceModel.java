package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.ColorEnum;
import com.example.mobilele.model.entity.enums.ConditionEnum;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

public class OffersFindServiceModel {
  private EngineEnum engine;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private String model;
  private String brand;
  private Integer year;
  private ConditionEnum condition;
  private ColorEnum color;

  public OffersFindServiceModel() {

  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public ColorEnum getColor() {
    return color;
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

  public OffersFindServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OffersFindServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OffersFindServiceModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OffersFindServiceModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OffersFindServiceModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OffersFindServiceModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public OffersFindServiceModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OffersFindServiceModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OffersFindServiceModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }
}
