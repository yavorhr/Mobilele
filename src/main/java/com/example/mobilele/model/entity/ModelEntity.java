package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "models")
public class ModelEntity extends BaseEntity {
  private String name;
  private CategoryTypeEnum category;
  private String imageUrl;
  private Integer startYear;
  private Integer endYear;
  private Brand brand;

  public ModelEntity() {
  }

  @ManyToOne(cascade = CascadeType.PERSIST)
  public Brand getBrand() {
    return brand;
  }

  @Column(nullable = false, unique = true)
  public String getName() {
    return name;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public CategoryTypeEnum getCategory() {
    return category;
  }

  @Column(name = "image_url", nullable = false)
  public String getImageUrl() {
    return imageUrl;
  }

  @Column(name = "start_year", nullable = false)
  public Integer getStartYear() {
    return startYear;
  }

  @Column(name = "end_year")
  public Integer getEndYear() {
    return endYear;
  }

  public ModelEntity setName(String name) {
    this.name = name;
    return this;
  }

  public ModelEntity setCategory(CategoryTypeEnum category) {
    this.category = category;
    return this;
  }

  public ModelEntity setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public ModelEntity setStartYear(Integer startYear) {
    this.startYear = startYear;
    return this;
  }

  public ModelEntity setEndYear(Integer endYear) {
    this.endYear = endYear;
    return this;
  }

  public ModelEntity setBrand(Brand brand) {
    this.brand = brand;
    return this;
  }
}
