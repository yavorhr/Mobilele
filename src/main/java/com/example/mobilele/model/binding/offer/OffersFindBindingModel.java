package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.ColorEnum;
import com.example.mobilele.model.entity.enums.ConditionEnum;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OffersFindBindingModel {
  private String brand;
  private String model;
  private Double price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private ColorEnum color;
  private ConditionEnum condition;
  private String location;

  private String mileageComparison;
  private Double mileageMax;

  private String priceComparison;
  private Double priceMax;

  private String yearComparison;
  private Integer yearMax;

  public OffersFindBindingModel() {
  }

  public String getLocation() {
    return location;
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

  @NotNull
  @NotBlank
  public String getBrand() {
    return brand;
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

  public OffersFindBindingModel setYear(Integer modelYear) {
    this.year = modelYear;
    return this;
  }

  public OffersFindBindingModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OffersFindBindingModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }
}
