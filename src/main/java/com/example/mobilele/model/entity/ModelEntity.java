package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {
  private String name;
  private BrandEntity brand;
  private VehicleCategoryEnum vehicleType;
  private Integer year;
  private List<OfferEntity> offers;

  public ModelEntity() {
  }

  public ModelEntity(String name, VehicleCategoryEnum vehicleType, Integer year) {
    this.name = name;
    this.vehicleType = vehicleType;
    this.year = year;
  }

  @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
  public List<OfferEntity> getOffers() {
    return offers;
  }

  @Column(nullable = false)
  public Integer getYear() {
    return year;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "vehicle_type", nullable = false)
  public VehicleCategoryEnum getVehicleType() {
    return vehicleType;
  }

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  public BrandEntity getBrand() {
    return brand;
  }

  @Column(nullable = false, unique = true)
  public String getName() {
    return name;
  }

  public ModelEntity setName(String name) {
    this.name = name;
    return this;
  }

  public ModelEntity setOffers(List<OfferEntity> offers) {
    this.offers = offers;
    return this;
  }

  public ModelEntity setBrand(BrandEntity brand) {
    this.brand = brand;
    return this;
  }

  public ModelEntity setVehicleType(VehicleCategoryEnum vehicleType) {
    this.vehicleType = vehicleType;
    return this;
  }

  public ModelEntity setYear(Integer year) {
    this.year = year;
    return this;
  }
}
