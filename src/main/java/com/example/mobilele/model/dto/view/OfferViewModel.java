package com.example.mobilele.model.dto.view;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import java.time.Instant;

public class OfferViewModel {
  private Long id;
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

  public OfferViewModel() {
  }

  public Long getId() {
    return id;
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

  public OfferViewModel setModelBrandName(String modelBrandName) {
    this.modelBrandName = modelBrandName;
    return this;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OfferViewModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferViewModel setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
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
    return modelBrandName;
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
