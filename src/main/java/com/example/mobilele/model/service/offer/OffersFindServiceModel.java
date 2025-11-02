package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.*;
import java.math.BigDecimal;

public class OffersFindServiceModel {
  private String brand;
  private String model;
  private BigDecimal price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private ColorEnum color;
  private ConditionEnum condition;
  private CountryEnum country;
  private CityEnum city;

  private String mileageComparison;
  private Double mileageMax;

  private String priceComparison;
  private BigDecimal priceMax;

  private String yearComparison;
  private Integer yearMax;

  public OffersFindServiceModel() {
  }

  public CountryEnum getCountry() {
    return country;
  }

  public CityEnum getCity() {
    return city;
  }

  public String getMileageComparison() {
    return mileageComparison;
  }

  public Double getMileageMax() {
    return mileageMax;
  }

  public String getPriceComparison() {
    return priceComparison;
  }

  public BigDecimal getPriceMax() {
    return priceMax;
  }

  public String getYearComparison() {
    return yearComparison;
  }

  public Integer getYearMax() {
    return yearMax;
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

  public BigDecimal getPrice() {
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

  public OffersFindServiceModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OffersFindServiceModel setCity(CityEnum city) {
    this.city = city;
    return this;
  }

  public OffersFindServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OffersFindServiceModel setPrice(BigDecimal price) {
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

  public OffersFindServiceModel setMileageComparison(String mileageComparison) {
    this.mileageComparison = mileageComparison;
    return this;
  }

  public OffersFindServiceModel setMileageMax(Double mileageMax) {
    this.mileageMax = mileageMax;
    return this;
  }

  public OffersFindServiceModel setPriceComparison(String priceComparison) {
    this.priceComparison = priceComparison;
    return this;
  }

  public OffersFindServiceModel setPriceMax(BigDecimal priceMax) {
    this.priceMax = priceMax;
    return this;
  }

  public OffersFindServiceModel setYearComparison(String yearComparison) {
    this.yearComparison = yearComparison;
    return this;
  }

  public OffersFindServiceModel setYearMax(Integer yearMax) {
    this.yearMax = yearMax;
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
