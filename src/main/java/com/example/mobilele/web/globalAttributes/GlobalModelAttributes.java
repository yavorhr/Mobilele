package com.example.mobilele.web.globalAttributes;

import com.example.mobilele.model.binding.offer.OfferAddBindingModel;
import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.web.OffersApiController;
import com.example.mobilele.web.OffersController;
import com.example.mobilele.web.OffersSearchController;
import com.example.mobilele.web.OffersUserController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(assignableTypes = {
        OffersController.class,
        OffersSearchController.class,
        OffersUserController.class,
        OffersApiController.class
})
public class GlobalModelAttributes {

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
