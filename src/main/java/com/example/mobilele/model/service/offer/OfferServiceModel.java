package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.ColorEnum;
import com.example.mobilele.model.entity.enums.ConditionEnum;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import java.time.Instant;

public class OfferServiceModel {
  private Long id;
  private Double price;
  private String modelBrandName;
  private String description;
  private EngineEnum engine;
  private Double mileage;
  private TransmissionType transmission;
  private ConditionEnum condition;
  private ColorEnum color;
  private Integer year;
  private String modelName;
  private Instant created;
  private Instant modified;
  private String sellerFirstName;
  private String sellerLastName;
  private String sellerFullName;
  private boolean canModify;

  public OfferServiceModel() {
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public ColorEnum getColor() {
    return color;
  }

  public String getSellerFullName() {
    return sellerFullName;
  }

  public void setSellerFullName() {
    this.sellerFullName = sellerFirstName + " " + sellerLastName;
  }

  public String getSellerFirstName() {
    return sellerFirstName;
  }

  public String getSellerLastName() {
    return sellerLastName;
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

  public String getModelBrandName() {
    return modelBrandName;
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

  public Integer getYear() {
    return year;
  }

  public String getModelName() {
    return modelName;
  }

  public Instant getCreated() {
    return created;
  }

  public Instant getModified() {
    return modified;
  }


  public OfferServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public OfferServiceModel setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferServiceModel setModelBrandName(String modelBrandName) {
    this.modelBrandName = modelBrandName;
    return this;
  }

  public OfferServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferServiceModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferServiceModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferServiceModel setSellerFullName(String sellerFullName) {
    this.sellerFullName = sellerFullName;
    return this;
  }

  public OfferServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferServiceModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferServiceModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferServiceModel setYear(Integer year) {
    this.year = year;
    return this;
  }

  public OfferServiceModel setModelName(String modelName) {
    this.modelName = modelName;
    return this;
  }

  public OfferServiceModel setCreated(Instant created) {
    this.created = created;
    return this;
  }

  public OfferServiceModel setModified(Instant modified) {
    this.modified = modified;
    return this;
  }

  public OfferServiceModel setSellerFirstName(String sellerFirstName) {
    this.sellerFirstName = sellerFirstName;
    return this;
  }

  public OfferServiceModel setSellerLastName(String sellerLastName) {
    this.sellerLastName = sellerLastName;
    return this;
  }

  public OfferServiceModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }
}
