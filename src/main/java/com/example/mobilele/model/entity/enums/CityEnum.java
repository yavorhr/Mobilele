package com.example.mobilele.model.entity.enums;

public enum CityEnum {
  // Bulgaria
  SOFIA(CountryEnum.BULGARIA),
  PLOVDIV(CountryEnum.BULGARIA),
  VARNA(CountryEnum.BULGARIA),

  // Germany
  BERLIN(CountryEnum.GERMANY),
  MUNICH(CountryEnum.GERMANY),
  HAMBURG(CountryEnum.GERMANY),

  // Italy
  ROME(CountryEnum.ITALY),
  MILAN(CountryEnum.ITALY),

  // France
  PARIS(CountryEnum.FRANCE),
  LYON(CountryEnum.FRANCE),

  // Spain
  MADRID(CountryEnum.SPAIN),
  BARCELONA(CountryEnum.SPAIN);

  private final CountryEnum country;

  CityEnum(CountryEnum country) {
    this.country = country;
  }

  public CountryEnum getCountry() {
    return country;
  }
}
