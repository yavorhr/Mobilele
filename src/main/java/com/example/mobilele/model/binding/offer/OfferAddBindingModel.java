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

  @NotNull(message = "Please select vehicle category")
  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

  @NotBlank(message = "Please select model")
  public String getModel() {
    return model;
  }

  @NotBlank(message = "Please select brand")
  public String getBrand() {
    return brand;
  }

  @NotNull(message = "Please upload at least one picture")
  public List<MultipartFile> getPictures() {
    return pictures;
  }

  @NotNull(message = "Please select condition")
  public ConditionEnum getCondition() {
    return condition;
  }

  @Size(min = 10, message = "Description must be at least 10 characters long")
  public String getDescription() {
    return description;
  }

  @NotNull(message = "Please select engine type")
  public EngineEnum getEngine() {
    return engine;
  }

  @PositiveOrZero(message = "Mileage must be zero or positive")
  @NotNull(message = "Please enter mileage")
  public Double getMileage() {
    return mileage;
  }

  @NotNull(message = "Please insert price")
  @DecimalMin(value = "100", message = "Price must be at least 100")
  public BigDecimal getPrice() {
    return price;
  }

  @NotNull(message = "Please select transmission type")
  public TransmissionType getTransmission() {
    return transmission;
  }

  @ValidYear
  @NotNull(message = "Please enter model year")
  public Integer getYear() {
    return year;
  }

  @NotNull(message = "Please select color")
  public ColorEnum getColor() {
    return color;
  }

  @NotNull(message = "Please choose country")
  public CountryEnum getCountry() {
    return country;
  }

  @NotNull(message = "Please choose city")
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
