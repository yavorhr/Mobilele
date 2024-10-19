package com.example.mobilele.model.dto.binding.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.validation.constraints.*;

public class OfferAddBindingModel {
  private Long id;
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private Integer year;
  private String model;
  private String brand;

  public OfferAddBindingModel() {
  }

  public Long getId() {
    return id;
  }

  @NotNull
  @NotBlank
  public String getModel() {
    return model;
  }

  @NotNull
  @NotBlank
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

  public OfferAddBindingModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferAddBindingModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferAddBindingModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public OfferAddBindingModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferAddBindingModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferAddBindingModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferAddBindingModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferAddBindingModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OfferAddBindingModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public OfferAddBindingModel setId(Long id) {
    this.id = id;
    return this;
  }
}
