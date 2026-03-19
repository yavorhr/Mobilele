package com.example.mobilele.service.impl;

import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

  @Test
  void testDoesOfferExistInUsersFavorites() {
    when(userRepository.existsByUsernameAndFavorites_Id("user", 1L)).thenReturn(true);

    boolean result = favoritesService.doesOfferExistInUsersFavorites(1L, "user");

    assertTrue(result);
  }
}
