package com.example.mobilele.service;

import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface OfferService {

  void initOffers();

  List<OfferViewModel> findAllOffers();

  OfferViewModel findOfferById(String name, Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, String username) throws IOException;

  Collection<OfferViewModel> findOffersByBrandAndVehicleType(String brand, VehicleCategoryEnum vehicleType);

  void updateOffer(OfferUpdateServiceModel serviceModel, Long id);

  List<OfferBaseViewModel> findOffersByFilters(OffersFindServiceModel offersFindServiceModel, VehicleCategoryEnum vehicleCategory);

  boolean isOwnerOrIsAdmin(String userName, Long id);

  OfferEntity findById(long id);

  List<OfferBaseViewModel> findOffersByBrand(String toUpperCase);
}
