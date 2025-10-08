package com.example.mobilele.model.view.model;
import com.example.mobilele.model.service.offer.OfferServiceModelWithIdAndUrl;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;

import java.util.List;

public class ModelViewModel {
  private Long id;
  private String name;
  private VehicleCategoryEnum category;
  private Integer startYear;
  private Integer endYear;
  private List<OfferServiceModelWithIdAndUrl> offers;

  public ModelViewModel() {
  }

  public String getName() {
    return name.substring(0,1).toUpperCase() + name.substring(1);
  }

  public List<OfferServiceModelWithIdAndUrl> getOffers() {
    return offers;
  }

  public Long getId() {
    return id;
  }

  public VehicleCategoryEnum getCategory() {
    return category;
  }

  public Integer getStartYear() {
    return startYear;
  }

  public Integer getEndYear() {
    return endYear;
  }

  public ModelViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public ModelViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public ModelViewModel setCategory(VehicleCategoryEnum category) {
    this.category = category;
    return this;
  }

  public ModelViewModel setStartYear(Integer startYear) {
    this.startYear = startYear;
    return this;
  }

  public ModelViewModel setEndYear(Integer endYear) {
    this.endYear = endYear;
    return this;
  }

  public ModelViewModel setOffers(List<OfferServiceModelWithIdAndUrl> offers) {
    this.offers = offers;
    return this;
  }
}
