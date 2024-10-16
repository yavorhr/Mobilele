package com.example.mobilele.model.entity.enums;

public enum EngineEnum {
  GASOLINE("Gasoline"),
  DIESEL("Diesel"),
  ELECTRIC("Electric"),
  HYBRID("Hybrid");

  private final String engineType;

  EngineEnum(String engineType) {
    this.engineType = engineType;
  }

  public String getEngineType() {
    return engineType;
  }
}
