package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.enums.ColorEnum;
import com.example.mobilele.model.entity.enums.ConditionEnum;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.model.service.PictureServiceModel;
import com.example.mobilele.model.view.user.UserViewModel;

import java.time.Instant;
import java.util.List;

public class OfferViewModel {
  private Long id;
  private Double price;
  private String description;
  private String modelBrandName;
  private String modelVehicleType;
  private EngineEnum engine;
  private String location;
  private Double mileage;
  private TransmissionType transmission;
  private Integer modelYear;
  private String modelName;
  private Instant created;
  private Instant modified;
  private boolean canModify;
  private List<PictureServiceModel> pictures;
  private ColorEnum color;
  private ConditionEnum condition;
  private UserViewModel seller;

  public OfferViewModel() {
  }

  public String getModelVehicleType() {
    return modelVehicleType;
  }

  public String getLocation() {
    return location;
  }

  public Integer getModelYear() {
    return modelYear;
  }

  public OfferViewModel setModelYear(Integer modelYear) {
    this.modelYear = modelYear;
    return this;
  }

  public List<PictureServiceModel> getPictures() {
    return pictures;
  }

  public UserViewModel getSeller() {
    return seller;
  }

  public ColorEnum getColor() {
    return color;
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public boolean isCanModify() {
    return canModify;
  }

  public Long getId() {
    return id;
  }

  public Double getPrice() {
    return price;
  }

  public Instant getCreated() {
    return created;
  }

  public Instant getModified() {
    return modified;
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

  public TransmissionType getTransmission() {
    return transmission;
  }

  public int getYear() {
    return modelYear;
  }

  public String getModelName() {
    return modelName.substring(0, 1).toUpperCase() + modelName.substring(1).toLowerCase();
  }

  public OfferViewModel setModelVehicleType(String modelVehicleType) {
    this.modelVehicleType = modelVehicleType;
    return this;
  }

  public OfferViewModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferViewModel setSeller(UserViewModel seller) {
    this.seller = seller;
    return this;
  }

  public OfferViewModel setLocation(String location) {
    this.location = location;
    return this;
  }

  public OfferViewModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }

  public OfferViewModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferViewModel setModelBrandName(String modelBrandName) {
    this.modelBrandName = modelBrandName;
    return this;
  }

  public OfferViewModel setPictures(List<PictureServiceModel> pictures) {
    this.pictures = pictures;
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OfferViewModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferViewModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferViewModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferViewModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferViewModel setYear(int year) {
    this.modelYear = year;
    return this;
  }

  public OfferViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferViewModel setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public String getModelBrandName() {
    return modelBrandName.substring(0, 1).toUpperCase() + modelBrandName.substring(1).toLowerCase();
  }

  public OfferViewModel setCreated(Instant created) {
    this.created = created;
    return this;
  }

  public OfferViewModel setModified(Instant modified) {
    this.modified = modified;
    return this;
  }
}
