package com.example.mobilele.model.binding.offer;

import com.example.mobilele.model.entity.enums.*;

import java.math.BigDecimal;

public class OffersQuickSearchBindingModel {
  private VehicleCategoryEnum vehicleType;
  private String brand;
  private BigDecimal price;
  private Integer year;

  public OffersQuickSearchBindingModel() {
  }

  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

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

  public OffersQuickSearchBindingModel setBrand(String brand) {
    this.brand = brand;
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
}
