package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "models")
public class Model extends BaseEntity {

  private String name;
  private CategoryTypeEnum category;
  private String imageUrl;
  private Integer startYear;
  private Integer endYear;
  private Instant created;
  private Instant modified;
  private Brand brand;

  public Model() {
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

  @Column(columnDefinition = "DATETIME")
  public Instant getCreated() {
    return created;
  }

  @Column(columnDefinition = "DATETIME")
  public Instant getModified() {
    return modified;
  }

  @ManyToOne
  public Brand getBrand() {
    return brand;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCategory(CategoryTypeEnum category) {
    this.category = category;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setStartYear(Integer startYear) {
    this.startYear = startYear;
  }

  public void setEndYear(Integer endYear) {
    this.endYear = endYear;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public void setModified(Instant modified) {
    this.modified = modified;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  @PostConstruct
  private void setCreatedTime() {
    setCreated(Instant.now());
  }
}
