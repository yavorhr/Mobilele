package com.example.mobilele.model.service.offer;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

import java.time.Instant;

public class OfferServiceModel {
  private Long id;
  private Double price;
  private String modelBrandName;
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private TransmissionType transmission;
  private Integer year;
  private String modelName;
  private Instant created;
  private Instant modified;
  private String sellerFullName;
  private boolean canModify;

  public OfferServiceModel() {
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

  public String getImageUrl() {
    return imageUrl;
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

  public String getSellerFullName() {
    return sellerFullName;
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

  public OfferServiceModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferServiceModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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

  public OfferServiceModel setSellerFullName(String sellerFullName) {
    this.sellerFullName = sellerFullName;
    return this;
  }

  public OfferServiceModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }
}
