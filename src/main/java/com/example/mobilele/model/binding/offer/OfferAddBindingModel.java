package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.validator.ValidYear;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class OfferAddBindingModel {
  private String brand;
  private String model;
  private BigDecimal price;
  private Double mileage;
  private EngineEnum engine;
  private TransmissionType transmission;
  private Integer year;
  private VehicleCategoryEnum vehicleType;
  private ConditionEnum condition;
  private ColorEnum color;
  private String description;
  private List<MultipartFile> pictures;
  private CountryEnum country;
  private CityEnum city;

  public OfferAddBindingModel() {
  }

  @NotNull(message = "{validation.vehicleType.required}")
  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

  @NotBlank(message = "{validation.model.required}")
  public String getModel() {
    return model;
  }

  @NotBlank(message = "{validation.brand.required}")
  public String getBrand() {
    return brand;
  }

  @NotNull(message = "{validation.pictures.required}")
  public List<MultipartFile> getPictures() {
    return pictures;
  }

  @NotNull(message = "{validation.condition.required}")
  public ConditionEnum getCondition() {
    return condition;
  }

  @Size(min = 10, message = "{validation.description.size}")
  public String getDescription() {
    return description;
  }

  @NotNull(message = "{validation.engine.required}")
  public EngineEnum getEngine() {
    return engine;
  }

  @PositiveOrZero(message = "{validation.mileage.positive}")
  @NotNull(message = "{validation.mileage.required}")
  public Double getMileage() {
    return mileage;
  }

  @NotNull(message = "{validation.price.required}")
  @DecimalMin(value = "100", message = "{validation.price.min}")
  public BigDecimal getPrice() {
    return price;
  }

  @NotNull(message = "{validation.transmission.required}")
  public TransmissionType getTransmission() {
    return transmission;
  }

  @ValidYear
  @NotNull(message = "{validation.year.required}")
  public Integer getYear() {
    return year;
  }

  @NotNull(message = "{validation.color.required}")
  public ColorEnum getColor() {
    return color;
  }

  @NotNull(message = "{validation.country.required}")
  public CountryEnum getCountry() {
    return country;
  }

  @NotNull(message = "{validation.city.required}")
  public CityEnum getCity() {
    return city;
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

  public OfferAddBindingModel setPrice(BigDecimal price) {
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

  public OfferAddBindingModel setVehicleType(VehicleCategoryEnum vehicleType) {
    this.vehicleType = vehicleType;
    return this;
  }

  public OfferAddBindingModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferAddBindingModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OfferAddBindingModel setCity(CityEnum city) {
    this.city = city;
    return this;
  }
}
