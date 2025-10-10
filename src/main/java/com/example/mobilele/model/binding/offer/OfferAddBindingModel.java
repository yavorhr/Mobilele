package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class OfferAddBindingModel {
  private String description;
  private EngineEnum engine;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private Integer year;
  private String model;
  private String brand;
  private VehicleCategoryEnum category;
  private ConditionEnum condition;
  private ColorEnum color;
  private List<MultipartFile> pictures;

  public OfferAddBindingModel() {
  }

  @NotNull
  @NotEmpty
  public List<MultipartFile> getPictures() {
    return pictures;
  }

  @NotNull
  public ConditionEnum getCondition() {
    return condition;
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
  @Size(min = 10)
  public String getDescription() {
    return description;
  }

  @NotNull
  public EngineEnum getEngine() {
    return engine;
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
  @NotNull
  @ValidYear
  public Integer getYear() {
    return year;
  }

  @NotNull
  public VehicleCategoryEnum getCategory() {
    return category;
  }

  @NotNull
  public ColorEnum getColor() {
    return color;
  }

  public OfferAddBindingModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferAddBindingModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public OfferAddBindingModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferAddBindingModel setEngine(EngineEnum engine) {
    this.engine = engine;
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

  public OfferAddBindingModel setCategory(VehicleCategoryEnum category) {
    this.category = category;
    return this;
  }

  public OfferAddBindingModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }
}
