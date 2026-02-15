package com.example.mobilele.model.view;

public class CityResponse {
  private String value;
  private String label;

  public CityResponse(String value, String label) {
    this.value = value;
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return label;
  }

  public CityResponse setValue(String value) {
    this.value = value;
    return this;
  }

  public CityResponse setLabel(String label) {
    this.label = label;
    return this;
  }
}
