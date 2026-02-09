package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.validator.ValidYear;
import jakarta.validation.constraints.*;

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

  @NotNull(message = "Please enter price")
  @DecimalMin(value = "100", message = "Price must be at least 100")
  public BigDecimal getPrice() {
    return price;
  }

  @PositiveOrZero(message = "Mileage must be zero or positive")
  @NotNull(message = "Please enter mileage")
  public Double getMileage() {
    return mileage;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  @ValidYear
  public Integer getYear() {
    return year;
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public ColorEnum getColor() {
    return color;
  }

  @Size(min = 10, message = "Description must be at least 10 characters long")
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
