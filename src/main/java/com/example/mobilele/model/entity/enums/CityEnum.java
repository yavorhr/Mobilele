package com.example.mobilele.model.entity.enums;

public enum CityEnum {
  // Bulgaria
  Sofia(CountryEnum.Bulgaria),
  Plovdiv(CountryEnum.Bulgaria),
  Varna(CountryEnum.Bulgaria),

  // Germany
  Berlin(CountryEnum.Germany),
  Munich(CountryEnum.Germany),
  Hamburg(CountryEnum.Germany),

  // Italy
  Rome(CountryEnum.Italy),
  Milan(CountryEnum.Italy),

  // France
  Paris(CountryEnum.France),
  Lyon(CountryEnum.France),

  // Spain
  Madrid(CountryEnum.Spain),
  Barcelona(CountryEnum.Spain);

  private final CountryEnum country;

  CityEnum(CountryEnum country) {
    this.country = country;
  }

  public CountryEnum getCountry() {
    return country;
  }
}
