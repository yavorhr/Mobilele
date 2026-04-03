package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.OffersFindBindingModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser
class OffersSearchControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @MockBean
  private ModelService modelService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private MessageSource messageSource;

  // =========================
  // GET OFFERS/FIND
  // =========================

  @Test
  void getFindOffersView_shouldReturnCategoriesPage() throws Exception {

    mockMvc.perform(get("/offers/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers-categories"));
  }

  // ============================
  // GET OFFERS/FIND/VEHICLE TYPE
  // ============================

  @Test
  void getSearchFormByCategory_shouldReturnFormWithVehicleType() throws Exception {

    mockMvc.perform(get("/offers/find/CAR"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers-find"))
            .andExpect(model().attributeExists("vehicleType"));
  }

  // ========================================
  // POST OFFERS/FIND/VEHICLE TYPE - INVALID
  // ========================================

  @Test
  void submitFindOffers_shouldRedirectBack_whenInvalid() throws Exception {

    mockMvc.perform(post("/offers/find/CAR")
            .param("brand", "")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/find/CAR"))
            .andExpect(flash().attributeExists("offersFindBindingModel"));
  }

  // =======================================
  // POST OFFERS/FIND/VEHICLE TYPE - SUCCESS
  // =======================================

  @Test
  void submitFindOffers_shouldRedirectToResults_whenValid() throws Exception {

    mockMvc.perform(post("/offers/find/CAR")
            .param("brand", "BMW")
            .param("model", "M3")
            .param("price", "1000")
            .param("mileage", "1000")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/find/CAR/bmw/m3"));
  }

  // ==========================================
  // RESULT - FILTERED OFFERS (WITH FLASH ATTR.)
  // ==========================================

  @Test
  void showOffersByModel_shouldUseFilters_whenPresent() throws Exception {

    Page<OfferBaseViewModel> page =
            new PageImpl<>(List.of(new OfferBaseViewModel()));

    when(modelMapper.map(any(), eq(OffersFindServiceModel.class)))
            .thenReturn(new OffersFindServiceModel());

    when(offerService.findOffersByFilters(any(), any(), any()))
            .thenReturn(page);

    when(messageSource.getMessage(any(), any(), any()))
            .thenReturn("Title");

    OffersFindBindingModel filters = new OffersFindBindingModel();
    filters.setBrand("BMW");
    filters.setModel("M3");

    mockMvc.perform(get("/offers/find/CAR/bmw/m3")
            .flashAttr("filters", filters))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"));

    verify(offerService).findOffersByFilters(any(), any(), any());
  }

  // ==========================================
  // RESULT - FILTERED OFFERS - WITHOUT FILTERS
  // ==========================================

  @Test
  @WithMockUser
  void showOffersByModel_shouldUseDirectSearch_whenNoFilters() throws Exception {

    Page<OfferBaseViewModel> page =
            new PageImpl<>(List.of(new OfferBaseViewModel()));

    when(offerService.findByTypeBrandAndModel(any(), any(), any(), any()))
            .thenReturn(page);

    when(messageSource.getMessage(any(), any(), any()))
            .thenReturn("Title");

    mockMvc.perform(get("/offers/find/CAR/bmw/m3"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"));

    verify(offerService).findByTypeBrandAndModel(any(), any(), any(), any());
  }

  // =============================
  // POST - QUICK SEARCH SUCCESS
  // =============================

  @Test
  @WithMockUser
  void quickSearch_shouldRedirectToResults_whenValid() throws Exception {

    mockMvc.perform(post("/offers/quick-search")
            .param("vehicleType", "car")
            .param("brand", "BMW")
            .param("model", "M3")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/find/car/bmw/m3"));
  }

  // =============================
  // POST - QUICK SEARCH INVALID
  // =============================

  @Test
  @WithMockUser
  void quickSearch_shouldRedirectHome_whenInvalid() throws Exception {

    mockMvc.perform(post("/offers/quick-search")
            .param("vehicleType", "car")
            .param("brand", "") // invalid
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
  }

  // =============
  // BRAND SEARCH
  // =============

  @Test
  @WithMockUser
  void getOffersByBrand_shouldReturnOffersPage() throws Exception {

    Page<OfferBaseViewModel> page =
            new PageImpl<>(List.of(new OfferBaseViewModel()));

    when(offerService.findOffersByBrand(any(), any()))
            .thenReturn(page);

    when(messageSource.getMessage(any(), any(), any()))
            .thenReturn("Brand Title");

    mockMvc.perform(get("/offers/brands/bmw"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(model().attributeExists("title"));
  }
}