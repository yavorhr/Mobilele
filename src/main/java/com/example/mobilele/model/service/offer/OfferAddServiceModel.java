package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public class OfferAddServiceModel {
  private Long id;
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

  public OfferAddServiceModel() {
  }

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  public ColorEnum getColor() {
    return color;
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
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

  public Integer getYear() {
    return year;
  }

  public String getModel() {
    return model;
  }

  public String getBrand() {
    return brand;
  }

  public CountryEnum getCountry() {
    return country;
  }

  public CityEnum getCity() {
    return city;
  }

  public OfferAddServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferAddServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferAddServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferAddServiceModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public OfferAddServiceModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferAddServiceModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferAddServiceModel setVehicleType(VehicleCategoryEnum vehicleType) {
    this.vehicleType = vehicleType;
    return this;
  }

  public OfferAddServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferAddServiceModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public OfferAddServiceModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferAddServiceModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OfferAddServiceModel setCity(CityEnum city) {
    this.city = city;
    return this;
  }

  public OfferAddServiceModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferAddServiceModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OfferAddServiceModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }
}
