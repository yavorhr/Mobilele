package com.example.mobilele.service;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.view.offer.OfferViewModel;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface OfferService {

  void initOffers();

  List<OfferViewModel> findAllOffers();

  OfferServiceModel findOfferById(String name, Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, String username) throws IOException;

  Collection<OfferServiceModel> findOffersByBrand(String brand);

  void updateOffer(OfferUpdateServiceModel serviceModel, Long id);

  boolean isOwner(String userName, Long id);

  OfferEntity findById(long id);
}
