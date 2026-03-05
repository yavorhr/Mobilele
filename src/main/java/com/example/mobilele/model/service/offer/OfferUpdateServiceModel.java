package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.*;

import java.math.BigDecimal;

public class OfferUpdateServiceModel {
  private BigDecimal price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private ConditionEnum condition;
  private ColorEnum color;
  private CountryEnum country;
  private CityEnum city;
  private String description;

  public OfferUpdateServiceModel() {
  }

  public ConditionEnum getCondition() {
    return condition;
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

  public OfferUpdateServiceModel setCondition(ConditionEnum condition) {
    this.condition = condition;
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
}
