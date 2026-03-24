package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FavoritesServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private OfferRepository offerRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private FavoritesServiceImpl favoritesService;

  OfferEntity offer;

  UserEntity user;

  @BeforeEach
  void init() {
    offer = new OfferEntity();
    offer.setReserved(false);
    offer.setId(1L);
    offer.setFavoritedBy(new HashSet<>());

    user = new UserEntity();
    user.setUsername("user");
  }

  @Test
  void testDoesOfferExistInUsersFavorites() {
    when(userRepository.existsByUsernameAndFavorites_Id("user", 1L)).thenReturn(true);

    boolean result = favoritesService.doesOfferExistInUsersFavorites(1L, "user");

    assertTrue(result);
  }

  @Test
  void testToggleReservation_success() {
    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    boolean result = favoritesService.toggleReservation(1L, "user");

    assertTrue(result);
    assertTrue(offer.isReserved());
    verify(offerRepository).save(offer);
  }

  @Test
  void testToggleReservation_notFound() {
    when(offerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> favoritesService.toggleReservation(1L, "user"));
  }

  @Test
  void testToggleFavorite_Add() {
    //Arrange
    when(userRepository.existsByUsernameAndFavorites_Id("user", 1L))
            .thenReturn(false);
    when(userRepository.findByUsername("user"))
            .thenReturn(Optional.of(user));
    when(offerRepository.findById(1L))
            .thenReturn(Optional.of(offer));

    //Act
    boolean result = favoritesService.toggleFavorite("user", 1L);

    //Assert
    assertTrue(result);
    assertTrue(user.getFavorites().contains(offer));
    assertTrue(offer.getFavoritedBy().contains(user));
  }

  @Test
  void testToggleFavorite_Remove() {

    user.setFavorites(new HashSet<>(Set.of(offer)));
    offer.setFavoritedBy(new HashSet<>(Set.of(user)));

    when(userRepository.existsByUsernameAndFavorites_Id("user", 1L))
            .thenReturn(true);
    when(userRepository.findByUsername("user"))
            .thenReturn(Optional.of(user));
    when(offerRepository.findById(1L))
            .thenReturn(Optional.of(offer));

    boolean result = favoritesService.toggleFavorite("user", 1L);

    assertFalse(result);
    assertFalse(user.getFavorites().contains(offer));
    assertFalse(offer.getFavoritedBy().contains(user));
  }

  @Test
  void testToggleFavorite_UserNotFound() {
    when(userRepository.findByUsername("user"))
            .thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> favoritesService.toggleFavorite("user", 1L));
  }

  @Test
  void testToggleFavorite_OfferNotFound() {
    when(userRepository.findByUsername("user"))
            .thenReturn(Optional.of(user));

    when(offerRepository.findById(1L))
            .thenReturn(Optional.empty());

    assertThrows(RuntimeException.class,
            () -> favoritesService.toggleFavorite("user", 1L));
  }

  @Test
  void testFindFavoriteOffers() {
    Pageable pageable = PageRequest.of(0, 2);

    Picture picture = new Picture();
    picture.setUrl("img.jpg");
    offer.setPictures(List.of(picture));

    OfferBaseViewModel viewModel = new OfferBaseViewModel();

    when(offerRepository.findFavoritesByUsername("user", pageable))
            .thenReturn(new PageImpl<>(List.of(offer)));

    when(modelMapper.map(offer, OfferBaseViewModel.class))
            .thenReturn(viewModel);

    Page<OfferBaseViewModel> result =
            favoritesService.findFavoriteOffers("user", pageable);

    assertEquals(1, result.getContent().size());
    assertEquals("img.jpg", result.getContent().get(0).getProfileImage());
  }
}
