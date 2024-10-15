package com.example.mobilele.model.dto.binding;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.validation.constraints.*;

public class OfferBindingModel {

  @NotNull
  @Size(min = 5)
  private String description;
  @NotNull
  private EngineEnum engine;
  @NotNull
  @NotEmpty
  private String imageUrl;
  @PositiveOrZero
  @NotNull
  private Double mileage;
  @NotNull
  @DecimalMin("100")
  private Double price;
  @NotNull
  private TransmissionType transmission;
  @Positive
  private Integer year;
  @NotNull
  @NotEmpty
  private String model;
  @NotNull
  @NotEmpty
  private String brand;

  public OfferBindingModel() {
  }

  public String getModel() {
    return model;
  }

  public String getBrand() {
    return brand;
  }

  public String getDescription() {
    return description;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public String getImageUrl() {
    return imageUrl;
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
