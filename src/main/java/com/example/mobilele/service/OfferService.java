package com.example.mobilele.service;

import com.example.mobilele.model.dto.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.dto.service.offer.OfferServiceModel;
import com.example.mobilele.model.dto.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.dto.view.offer.OfferViewModel;

import java.util.Collection;
import java.util.List;

public interface OfferService {

  void initOffers();

  List<OfferViewModel> findAllOffers();

  OfferServiceModel findOfferById(Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, Long id);

  Collection<OfferServiceModel> findOffersByBrand(String brand);

  void updateOffer(OfferUpdateServiceModel serviceModel, Long id);
}
