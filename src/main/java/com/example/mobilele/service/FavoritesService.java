package com.example.mobilele.service;

import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoritesService {

  boolean toggleFavorite(String name, Long offerId);

  boolean doesOfferExistInUsersFavorites(Long id, String name);

  Page<OfferBaseViewModel> findFavoriteOffers(String username, Pageable pageable);

  boolean toggleReservation(Long id, String username);
}
