package com.example.mobilele.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "brands")
public class BrandEntity extends BaseEntity {
  private String name;
  private Collection<ModelEntity> models;

  public BrandEntity() {
    this.models = new ArrayList<>();
  }

  @Column(unique = true, nullable = false)
  public String getName() {
    return name;
  }

  @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
  public Collection<ModelEntity> getModels() {
    return models;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BrandEntity setModels(Collection<ModelEntity> models) {
    this.models = models;
    return this;
  }
}
