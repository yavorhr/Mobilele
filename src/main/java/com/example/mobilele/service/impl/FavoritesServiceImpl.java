package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl implements FavoritesService {
  private final UserRepository userRepository;
  private final OfferRepository offerRepository;

  public FavoritesServiceImpl(UserRepository userRepository, OfferRepository offerRepository) {
    this.userRepository = userRepository;
    this.offerRepository = offerRepository;
  }

  @Override
  @Transactional
  public boolean toggleFavorite(String username, Long offerId) {
    boolean exists = userRepository.existsByUsernameAndFavorites_Id(username, offerId);

    UserEntity user = this.getByUsernameOrThrow(username);

    OfferEntity offer = offerRepository
            .findById(offerId)
            .orElseThrow(() -> new RuntimeException("Offer not found"));

    if (exists) {
      user.getFavorites().remove(offer);
      offer.getFavoritedBy().remove(user);
      return false;
    } else {
      user.getFavorites().add(offer);
      offer.getFavoritedBy().add(user);
      return true;
    }
  }

  private UserEntity getByUsernameOrThrow(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with username: " + username + " not found"));
  }
}
