package com.example.mobilele.model.dto.view;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;

public class OfferViewDto {
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private TransmissionType transmission;
  private Integer year;
  private ModelEntity model;
  private String brandName;

  public OfferViewDto() {
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

  public ModelEntity getModel() {
    return model;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
