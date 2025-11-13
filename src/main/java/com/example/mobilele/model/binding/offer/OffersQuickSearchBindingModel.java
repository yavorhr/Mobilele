package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class OffersQuickSearchBindingModel {
  private VehicleCategoryEnum vehicleType;
  private String brand;
  private String model;
  private BigDecimal price;
  private Integer year;

  private String priceComparison;
  private Double priceMax;

  private String yearComparison;
  private Integer yearMax;

  public OffersQuickSearchBindingModel() {
  }

  public String getModel() {
    return model;
  }

  @NotNull(message = "Vehicle type is required.")
  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

  @NotNull
  @NotEmpty(message = "Brand is required.")
  public String getBrand() {
    return brand;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Integer getYear() {
    return year;
  }

  public OffersQuickSearchBindingModel setVehicleType(VehicleCategoryEnum vehicleType) {
    this.vehicleType = vehicleType;
    return this;
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

  public OffersQuickSearchBindingModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public OffersQuickSearchBindingModel setModel(String model) {
    this.model = model;
    return this;
  }

  public OffersQuickSearchBindingModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public OffersQuickSearchBindingModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OffersQuickSearchBindingModel setPriceComparison(String priceComparison) {
    this.priceComparison = priceComparison;
    return this;
  }

  public OffersQuickSearchBindingModel setPriceMax(Double priceMax) {
    this.priceMax = priceMax;
    return this;
  }

  public OffersQuickSearchBindingModel setYearComparison(String yearComparison) {
    this.yearComparison = yearComparison;
    return this;
  }

  public OffersQuickSearchBindingModel setYearMax(Integer yearMax) {
    this.yearMax = yearMax;
    return this;
  }
}
