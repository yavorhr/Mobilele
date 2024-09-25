package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.EngineEnum;
import com.example.mobilele.model.entity.enums.TransmissionType;
import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
  private String description;
  private EngineEnum engine;
  private String imageUrl;
  private Double mileage;
  private Double price;
  private TransmissionType transmission;
  private Integer year;
  private Model model;
  private User seller;

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  @Enumerated
  @Column(nullable = false)
  public EngineEnum getEngine() {
    return engine;
  }

  @Column(name = "image_url", nullable = false)
  public String getImageUrl() {
    return imageUrl;
  }

  @Column(name = "mileage", nullable = false)
  public Double getMileage() {
    return mileage;
  }

  @Column(name = "price", nullable = false)
  public Double getPrice() {
    return price;
  }

  @Enumerated
  @Column(nullable = false)
  public TransmissionType getTransmission() {
    return transmission;
  }

  @Column(nullable = false)
  public Integer getYear() {
    return year;
  }

  @ManyToOne
  public Model getModel() {
    return model;
  }

  @OneToOne
  public User getSeller() {
    return seller;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEngine(EngineEnum engine) {
    this.engine = engine;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setMileage(Double mileage) {
    this.mileage = mileage;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setTransmission(TransmissionType transmission) {
    this.transmission = transmission;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  public void setSeller(User seller) {
    this.seller = seller;
  }


}
