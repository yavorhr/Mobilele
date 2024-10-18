package com.example.mobilele.model.dto.view.brand;

public class BrandViewNameModel {
  private String name;

  public BrandViewNameModel() {
  }

  public String getName() {
    return  name.substring(0,1).toUpperCase() + name.substring(1);
  }

  public BrandViewNameModel setName(String name) {
    this.name = name;
    return this;
  }
}
