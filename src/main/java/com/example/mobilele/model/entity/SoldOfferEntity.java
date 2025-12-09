package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "sold_offers")
public class SoldOfferEntity extends BaseEntity {
  private UserEntity seller;
  private ModelEntity model;
  private CountryEnum country;
  private CityEnum city;
  private BigDecimal price;
  private Double mileage;
  private String description;
  private EngineEnum engine;
  private ConditionEnum condition;
  private TransmissionType transmission;
  private ColorEnum color;
  private long views;
  private Instant saleTime;

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
  public CountryEnum getCountry() {
    return country;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public CityEnum getCity() {
    return city;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public EngineEnum getEngine() {
    return engine;
  }

  @Column(nullable = false)
  public long getViews() {
    return views;
  }

  @Column(name = "sale_time")
  public Instant getSaleTime() {
    return saleTime;
  }

  @Column(name = "mileage", nullable = false)
  public Double getMileage() {
    return mileage;
  }

  @Column(name = "price", nullable = false)
  public BigDecimal getPrice() {
    return price;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public TransmissionType getTransmission() {
    return transmission;
  }

  public SoldOfferEntity setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public SoldOfferEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public SoldOfferEntity setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public SoldOfferEntity setCity(CityEnum city) {
    this.city = city;
    return this;
  }

  public SoldOfferEntity setSaleTime(Instant saleTime) {
    this.saleTime = saleTime;
    return this;
  }

  public SoldOfferEntity setViews(long views) {
    this.views = views;
    return this;
  }

  public SoldOfferEntity setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public SoldOfferEntity setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public SoldOfferEntity setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public SoldOfferEntity setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public SoldOfferEntity setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public SoldOfferEntity setModel(ModelEntity model) {
    this.model = model;
    return this;
  }

  public SoldOfferEntity setSeller(UserEntity seller) {
    this.seller = seller;
    return this;
  }
}
