package com.example.mobilele.web.globalAttributes;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.web.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {
        OffersController.class,
        OffersSearchController.class,
        HomeController.class,
        OffersUserController.class,
        OffersApiController.class})
public class GlobalModelAttributes {

  private final BrandService brandService;

  public GlobalModelAttributes(BrandService brandService) {
    this.brandService = brandService;
  }

  @ModelAttribute("brands")
  public Object populateBrands() {
    return brandService.findAllBrands();
  }

  @ModelAttribute("offerBindingModel")
  public OfferAddBindingModel offerBindingModel() {
    return new OfferAddBindingModel();
  }

  @ModelAttribute("offersFindBindingModel")
  public OffersFindBindingModel offersFindBindingModel() {
    return new OffersFindBindingModel();
  }

  @ModelAttribute("engines")
  public EngineEnum[] getEngines() {
    return EngineEnum.values();
  }

  @ModelAttribute("transmissions")
  public TransmissionType[] getTransmissions() {
    return TransmissionType.values();
  }

  @ModelAttribute("categories")
  public VehicleCategoryEnum[] getVehicleCategories() {
    return VehicleCategoryEnum.values();
  }

  @ModelAttribute("colors")
  public ColorEnum[] getColors() {
    return ColorEnum.values();
  }

  @ModelAttribute("conditions")
  public ConditionEnum[] getConditions() {
    return ConditionEnum.values();
  }

  @ModelAttribute("countries")
  public CountryEnum[] getCountries() {
    return CountryEnum.values();
  }
}
