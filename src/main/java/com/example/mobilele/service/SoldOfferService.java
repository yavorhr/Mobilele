package com.example.mobilele.service;


import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import org.springframework.data.domain.Page;

public interface SoldOfferService {

  void seedSoldOffers();

  long getSoldVehiclesCount();

  Page<SoldOfferViewModel> getSoldCarsByYear(int year, int page);

  void saveSoldOffer(Long id);
}
