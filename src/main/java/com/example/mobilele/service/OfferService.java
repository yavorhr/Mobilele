package com.example.mobilele.service;

import com.example.mobilele.model.dto.service.OfferAddServiceModel;
import com.example.mobilele.model.dto.view.OfferViewModel;

import java.util.List;

public interface OfferService {

  void initOffers();

  List<OfferViewModel> findAllOffers();

  OfferViewModel findOfferById(Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, Long id);
}
