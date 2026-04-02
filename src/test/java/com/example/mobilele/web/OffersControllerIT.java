package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OffersControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @MockBean
  private SoldOfferService soldOfferService;

  @MockBean
  private FavoritesService favoritesService;

  @MockBean
  private ModelService modelService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean(name = "security")
  private SecurityService securityService;

  // =========================
  // GET ALL OFFERS
  // =========================

    @Test
    void showAllOffers_shouldReturnOffersPage() throws Exception {

      Page<OfferBaseViewModel> page =
              new PageImpl<>(List.of(new OfferBaseViewModel()));

      when(offerService.findAllOffers(any()))
              .thenReturn(page);

      mockMvc.perform(get("/offers/all"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attributeExists("totalPages"));
  }

  // =========================
  // TOP OFFERS
  // =========================

  @Test
  void showTopOffers_shouldReturnTopOffersPage() throws Exception {

    when(offerService.findTopOffersByViews())
            .thenReturn(List.of(new OfferBaseViewModel()));

    mockMvc.perform(get("/offers/top-offers"))
            .andExpect(status().isOk())
            .andExpect(view().name("top-offers"))
            .andExpect(model().attributeExists("offers"));
  }
}