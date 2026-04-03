package com.example.mobilele.web;

import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.service.OfferService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OffersUserControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @MockBean
  private FavoritesService favoritesService;

  @MockBean
  private MessageSource messageSource;

  // ==========
  // MY OFFERS
  // ==========

  @Test
  @WithMockUser(username = "user1")
  void showMyOffers_shouldReturnOffersPage() throws Exception {

    PageImpl<OfferBaseViewModel> page =
            new PageImpl<>(List.of(new OfferBaseViewModel()));

    when(offerService.findOffersByUserId(eq("user1"), any()))
            .thenReturn(page);

    when(messageSource.getMessage(any(), any(), any()))
            .thenReturn("My Offers");

    mockMvc.perform(get("/offers/my-offers"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(model().attribute("baseUrl", "/offers/my-offers"));

    verify(offerService).findOffersByUserId(eq("user1"), any());
  }

  // ==========
  // FAVORITES
  // ===========

  @Test
  @WithMockUser(username = "user1")
  void showFavoriteOffers_shouldReturnOffersPage() throws Exception {

    Page<OfferBaseViewModel> page =
            new PageImpl<>(List.of(new OfferBaseViewModel()));

    when(favoritesService.findFavoriteOffers(eq("user1"), any()))
            .thenReturn(page);

    when(messageSource.getMessage(any(), any(), any()))
            .thenReturn("Favorites");

    mockMvc.perform(get("/offers/favorites"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(model().attribute("baseUrl", "/offers/favorites"));

    verify(favoritesService).findFavoriteOffers(eq("user1"), any());
  }

  // =====================
  // UNAUTHORIZED ACCESS
  // =====================

  @Test
  void showMyOffers_shouldRedirectToLogin_whenAnonymous() throws Exception {

    mockMvc.perform(get("/offers/my-offers"))
            .andExpect(status().isFound());
  }

  @Test
  void showFavorites_shouldRedirectToLogin_whenAnonymous() throws Exception {

    mockMvc.perform(get("/offers/favorites"))
            .andExpect(status().isFound());
  }
}