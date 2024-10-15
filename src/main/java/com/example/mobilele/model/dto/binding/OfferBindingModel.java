package com.example.mobilele.model.dto.binding;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.validation.constraints.*;

public class OfferBindingModel {

  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private Double price;=
  private TransmissionType transmission;
  private Integer year;
  private String model;
  private String brand;

  public OfferBindingModel() {
  }
  
  @NotNull
  @NotEmpty
  public String getModel() {
    return model;
  }

  @NotNull
  @NotEmpty
  public String getBrand() {
    return brand;
  }

  @NotNull
  @Size(min = 5)
  public String getDescription() {
    return description;
  }

  @NotNull
  public EngineEnum getEngine() {
    return engine;
  }

  @NotNull
  @NotEmpty
  public String getImageUrl() {
    return imageUrl;
  }

  @PositiveOrZero
  @NotNull
  public Double getMileage() {
    return mileage;
  }

  @NotNull
  @DecimalMin("100")
  public Double getPrice() {
    return price;
  }

  @NotNull
  public TransmissionType getTransmission() {
    return transmission;
  }

  @Positive
  public Integer getYear() {
    return year;
  }

  public OfferBindingModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferBindingModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferBindingModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public OfferBindingModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferBindingModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferBindingModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferBindingModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferBindingModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OfferBindingModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }
}
