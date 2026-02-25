package com.example.mobilele.model.view.offer;

import com.example.mobilele.model.entity.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public class SoldOfferViewModel {

  private Long id;
  private String sellerName;
  private Long sellerId;
  private String email;
  private String phoneNumber;
  private String model;
  private String brand;
  private CountryEnum country;
  private CityEnum city;
  private BigDecimal price;
  private Double mileage;
  private EngineEnum engine;
  private ConditionEnum condition;
  private TransmissionType transmission;
  private ColorEnum color;
  private long views;
  private Instant saleTime;

  public SoldOfferViewModel() {
  }

  public SoldOfferViewModel(
          Long id,
          String sellerName,
          Long sellerId,
          String email,
          String phoneNumber,
          String model,
          String brand,
          CountryEnum country,
          CityEnum city,
          BigDecimal price,
          Double mileage,
          EngineEnum engine,
          ConditionEnum condition,
          TransmissionType transmission,
          ColorEnum color,
          long views,
          Instant saleTime) {
    this.id = id;
    this.sellerName = sellerName;
    this.sellerId = sellerId;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.model = model;
    this.brand = brand;
    this.country = country;
    this.city = city;
    this.price = price;
    this.mileage = mileage;
    this.engine = engine;
    this.condition = condition;
    this.transmission = transmission;
    this.color = color;
    this.views = views;
    this.saleTime = saleTime;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Long getId() {
    return id;
  }

  public String getSellerName() {
    return sellerName;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public String getEmail() {
    return email;
  }

  public String getModel() {
    return model;
  }

  public String getBrand() {
    return brand;
  }

  public CountryEnum getCountry() {
    return country;
  }

  public CityEnum getCity() {
    return city;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Double getMileage() {
    return mileage;
  }

  public EngineEnum getEngine() {
    return engine;
  }

  public ConditionEnum getCondition() {
    return condition;
  }

  public TransmissionType getTransmission() {
    return transmission;
  }

  public ColorEnum getColor() {
    return color;
  }

  public long getViews() {
    return views;
  }

  public Instant getSaleTime() {
    return saleTime;
  }

  public SoldOfferViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public SoldOfferViewModel setSellerName(String sellerName) {
    this.sellerName = sellerName;
    return this;
  }

  public SoldOfferViewModel setSellerId(Long sellerId) {
    this.sellerId = sellerId;
    return this;
  }

  public SoldOfferViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public SoldOfferViewModel setModel(String model) {
    this.model = model;
    return this;
  }

  public SoldOfferViewModel setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public SoldOfferViewModel setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public SoldOfferViewModel setCountry(CountryEnum country) {
    this.country = country;
    return this;
  }

  public SoldOfferViewModel setCity(CityEnum city) {
    this.city = city;
    return this;
  }

  public SoldOfferViewModel setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public SoldOfferViewModel setMileage(Double mileage) {
    this.mileage = mileage;
    return this;
  }

  public SoldOfferViewModel setEngine(EngineEnum engine) {
    this.engine = engine;
    return this;
  }

  public SoldOfferViewModel setCondition(ConditionEnum condition) {
    this.condition = condition;
    return this;
  }

  public SoldOfferViewModel setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
    return this;
  }

  public SoldOfferViewModel setColor(ColorEnum color) {
    this.color = color;
    return this;
  }

  public SoldOfferViewModel setViews(long views) {
    this.views = views;
    return this;
  }

  public SoldOfferViewModel setSaleTime(Instant saleTime) {
    this.saleTime = saleTime;
    return this;
  }
}
