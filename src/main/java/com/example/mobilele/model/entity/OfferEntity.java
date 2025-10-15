package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity {
  private String description;
  private EngineEnum engine;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private ModelEntity model;
  private Integer year;
  private Instant created;
  private Instant modified;
  private ConditionEnum condition;
  private UserEntity seller;
  private ColorEnum color;
  private List<Picture> pictures;

  public OfferEntity() {
  }

  @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
  public List<Picture> getPictures() {
    return pictures;
  }

  @ManyToOne
  public ModelEntity getModel() {
    return model;
  }

  @ManyToOne
  public UserEntity getSeller() {
    return seller;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public ColorEnum getColor() {
    return this.color;
  }

  @Column(nullable = false, name = "year")
  public Integer getYear() {
    return year;
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "vehicle_condition", nullable = false)
  public ConditionEnum getCondition() {
    return condition;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public EngineEnum getEngine() {
    return engine;
  }


  @Column(name = "mileage", nullable = false)
  public Double getMileage() {
    return mileage;
  }

  @Column(name = "price", nullable = false)
  public Double getPrice() {
    return price;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public TransmissionType getTransmission() {
    return transmission;
  }

  @Column(columnDefinition = "DATETIME")
  public Instant getCreated() {
    return created;
  }

  @Column(columnDefinition = "DATETIME")
  public Instant getModified() {
    return modified;
  }

  public OfferEntity setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public OfferEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public OfferEntity setPictures(List<Picture> pictures) {
    this.pictures = pictures;
    return this;
  }

  public OfferEntity setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public OfferEntity setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public OfferEntity setYear(Integer productionYear) {
    this.year = productionYear;
    return this;
  }

  public OfferEntity setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public OfferEntity setPrice(Double price) {
    this.price = price;
    return this;
  }

  public OfferEntity setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public OfferEntity setModel(ModelEntity model) {
    this.model = model;
    return this;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public void setModified(Instant modified) {
    this.modified = modified;
  }

  public OfferEntity setSeller(UserEntity seller) {
    this.seller = seller;
    return this;
  }

  @PrePersist
  private void preCreate() {
    setCreated(Instant.now());
  }

  @PreUpdate
  private void preUpdate() {
    setModified(Instant.now());
  }
}
