package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OffersFindBindingModel {
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
  private Double priceMax;

  private String yearComparison;
  private Integer yearMax;

  public OffersFindBindingModel() {
  }

  @NotNull
  @NotEmpty
  public String getBrand() {
    return brand;
  }

  @NotNull
  @NotEmpty
  public String getModel() {
    return model;
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

  public ColorEnum getColor() {
    return color;
  }

  public ConditionEnum getCondition() {
    return condition;
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

  public Double getPriceMax() {
    return priceMax;
  }

  public String getYearComparison() {
    return yearComparison;
  }

  public Integer getYearMax() {
    return yearMax;
  }

  public OffersFindBindingModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public OffersFindBindingModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OffersFindBindingModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public OffersFindBindingModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OffersFindBindingModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OffersFindBindingModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OffersFindBindingModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OffersFindBindingModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OffersFindBindingModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OffersFindBindingModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OffersFindBindingModel setCity(CityEnum city) {
    this.city = city;
    return this;
  }


  public OffersFindBindingModel setMileageComparison(String mileageComparison) {
    this.mileageComparison = mileageComparison;
    return this;
  }

  public OffersFindBindingModel setMileageMax(Double mileageMax) {
    this.mileageMax = mileageMax;
    return this;
  }

  public OffersFindBindingModel setPriceComparison(String priceComparison) {
    this.priceComparison = priceComparison;
    return this;
  }

  public OffersFindBindingModel setPriceMax(Double priceMax) {
    this.priceMax = priceMax;
    return this;
  }

  public OffersFindBindingModel setYearComparison(String yearComparison) {
    this.yearComparison = yearComparison;
    return this;
  }

  public OffersFindBindingModel setYearMax(Integer yearMax) {
    this.yearMax = yearMax;
    return this;
  }
}
