package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;

import java.math.BigDecimal;

public class OfferUpdateBindingForm {
  private Long id;
  private BigDecimal price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private ConditionEnum condition;
  private ColorEnum color;
  private String description;
  private CountryEnum country;
  private CityEnum city;

  public OfferUpdateBindingForm() {
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getPrice() {
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

  public OfferUpdateBindingForm setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public OfferUpdateBindingForm setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferUpdateBindingForm setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferUpdateBindingForm setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferUpdateBindingForm setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferUpdateBindingForm setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferUpdateBindingForm setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferUpdateBindingForm setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferUpdateBindingForm setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferUpdateBindingForm setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OfferUpdateBindingForm setCity(CityEnum city) {
    this.city = city;
    return this;
  }
}
