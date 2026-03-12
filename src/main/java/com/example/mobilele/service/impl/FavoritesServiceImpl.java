package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl implements FavoritesService {
  private final UserRepository userRepository;
  private final OfferRepository offerRepository;
  private final ModelMapper modelMapper;

  public FavoritesServiceImpl(UserRepository userRepository, OfferRepository offerRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.offerRepository = offerRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public boolean doesOfferExistInUsersFavorites(Long id, String username) {
    return userRepository.existsByUsernameAndFavorites_Id(username, id);
  }

  @Override
  public Page<OfferBaseViewModel> findFavoriteOffers(String username, Pageable pageable) {
    return offerRepository
            .findFavoritesByUsername(username, pageable)
            .map(this::mapToOfferBaseViewModel);
  }

  @Transactional
  @Override
  public boolean toggleReservation(Long id, String username) {
    OfferEntity offer = offerRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Offer not found"));

    offer.setReserved(!offer.isReserved());
    offerRepository.save(offer);

    return offer.isReserved();
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

  private OfferBaseViewModel mapToOfferBaseViewModel(OfferEntity e) {
    OfferBaseViewModel viewModel = this.modelMapper.map(e, OfferBaseViewModel.class);
    viewModel.setProfileImage(
            e.getPictures()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException("No pictures found for model " + e.getModel().getName() + " with ID: " + e.getId()))
                    .getUrl());

    return viewModel;
  }

  private UserEntity getByUsernameOrThrow(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with username: " + username + " not found"));
  }
}
