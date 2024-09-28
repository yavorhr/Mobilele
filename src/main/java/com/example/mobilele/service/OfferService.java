package com.example.mobilele.service;

import com.example.mobilele.model.dto.view.OfferViewModel;

import java.util.List;

public interface OfferService {

  void initOffers();

  List<OfferViewModel> findAllOffers();
}
