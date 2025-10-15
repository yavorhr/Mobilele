package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import com.example.mobilele.model.service.PictureServiceModel;

import java.time.Instant;
import java.util.List;

public class OfferViewModel {
  private Long id;
  private Double price;
  private String description;
  private String modelBrandName;
  private EngineEnum engine;
  private Double mileage;
  private TransmissionType transmission;
  private Integer year;
  private String modelName;
  private Instant created;
  private Instant modified;
  private boolean canModify;
  private String sellerFirstName;
  private String sellerLastName;
  private List<PictureServiceModel> pictures;

  public OfferViewModel() {
  }

  public List<PictureServiceModel> getPictures() {
    return pictures;
  }

  public String getSellerFirstName() {
    return sellerFirstName;
  }

  public String getSellerFullName() {
    return this.sellerFirstName + " " + this.sellerLastName;
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

  public Integer getYear() {
    return year;
  }

  public String getModelName() {
    return modelName.substring(0, 1).toUpperCase() + modelName.substring(1).toLowerCase();

  }

  public OfferViewModel setCanModify(boolean canModify) {
    this.canModify = canModify;
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

  public OfferViewModel setYear(Integer year) {
    this.year = year;
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


  public OfferViewModel setSellerFirstName(String sellerFirstName) {
    this.sellerFirstName = sellerFirstName;
    return this;
  }

  public OfferViewModel setSellerLastName(String sellerLastName) {
    this.sellerLastName = sellerLastName;
    return this;
  }
}
