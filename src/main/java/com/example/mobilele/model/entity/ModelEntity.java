package com.example.mobilele.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {
  private String name;
  private BrandEntity brand;
  private List<OfferEntity> offers;

  public ModelEntity() {
  }

  @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
  public List<OfferEntity> getOffers() {
    return offers;
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

}
