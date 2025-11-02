package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;

public class OfferEditBindingForm {
  private Long id;
  private Double price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private ConditionEnum condition;
  private ColorEnum color;
  private String description;
  private CountryEnum country;
  private CityEnum city;

  public OfferEditBindingForm() {
  }

  public Long getId() {
    return id;
  }

  public Double getPrice() {
    return price;
  }

  public Double getMileage() {
    return mileage;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  public Integer getYear() {
    return year;
  }

  public OfferEditBindingForm setId(Long id) {
    this.id = id;
    return this;
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public ColorEnum getColor() {
    return color;
  }

  public String getDescription() {
    return description;
  }

  public CountryEnum getCountry() {
    return country;
  }

  public CityEnum getCity() {
    return city;
  }

  public OfferEditBindingForm setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferEditBindingForm setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferEditBindingForm setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferEditBindingForm setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferEditBindingForm setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferEditBindingForm setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferEditBindingForm setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferEditBindingForm setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferEditBindingForm setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OfferEditBindingForm setCity(CityEnum city) {
    this.city = city;
    return this;
  }
}
