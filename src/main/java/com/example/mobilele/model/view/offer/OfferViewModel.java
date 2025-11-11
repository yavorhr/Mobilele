package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.model.service.PictureServiceModel;
import com.example.mobilele.model.view.user.UserViewModel;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OfferViewModel {
  private Long id;
  private UserViewModel seller;
  private List<PictureServiceModel> pictures;

  private Long views;
  private BigDecimal price;
  private String description;
  private String modelBrandName;
  private String modelVehicleType;
  private CityEnum city;
  private Double mileage;
  private Integer modelYear;
  private String modelName;

  private ColorEnum color;
  private EngineEnum engine;
  private CountryEnum country;
  private ConditionEnum condition;
  private TransmissionType transmission;

  private Instant created;
  private Instant modified;

  private boolean canModify;
  private boolean isNotOwnerOrIsAdmin;
  boolean isReserved;

  public OfferViewModel() {
  }

  public Long getViews() {
    return views;
  }

  public boolean isNotOwnerOrIsAdmin() {
    return isNotOwnerOrIsAdmin;
  }

  public boolean isReserved() {
    return isReserved;
  }


  public String getModelVehicleType() {
    return modelVehicleType;
  }

  public CountryEnum getCountry() {
    return country;
  }

  public CityEnum getCity() {
    return city;
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

  public BigDecimal getPrice() {
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

  public OfferViewModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public OfferViewModel setNotOwnerOrIsAdmin(boolean notOwnerOrIsAdmin) {
    isNotOwnerOrIsAdmin = notOwnerOrIsAdmin;
    return this;
  }

  public OfferViewModel setCity(CityEnum city) {
    this.city = city;
    return this;
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

  public OfferViewModel setViews(Long views) {
    this.views = views;
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

  public OfferViewModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }

  public OfferViewModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferViewModel setReserved(boolean reserved) {
    isReserved = reserved;
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

  public OfferViewModel setPrice(BigDecimal price) {
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
