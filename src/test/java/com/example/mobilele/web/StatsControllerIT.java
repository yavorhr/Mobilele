package com.example.mobilele.web;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import com.example.mobilele.service.StatsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class StatsControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StatsService statsService;

  @MockBean
  private OfferService offerService;

  @MockBean
  private SoldOfferService soldOfferService;

  //=========================
  // GET LIVE STATS - SUCCESS
  //=========================

  @Test
  @WithMockUser(roles = "ADMIN")
  void statistics_shouldReturnView_whenAdmin() throws Exception {

    when(statsService.getStats())
            .thenReturn(new StatsViewModel());

    mockMvc.perform(get("/admin/statistics"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/live-stats"))
            .andExpect(model().attributeExists("stats"));
  }

  //============================
  // GET LIVE STATS - FORBIDDEN
  //============================

  @Test
  @WithMockUser(roles = "USER")
  void statistics_shouldReturn403_whenNotAdmin() throws Exception {

    mockMvc.perform(get("/admin/statistics"))
            .andExpect(status().isForbidden());
  }

  //============================
  // SAVE SNAPSHOT - SUCCESS
  //============================

  @Test
  @WithMockUser(roles = "ADMIN")
  void saveStatsSnapshot_shouldRedirect() throws Exception {

    StatsViewModel stats = new StatsViewModel();

    when(statsService.getStats()).thenReturn(stats);
    doNothing().when(statsService).saveSnapshot(any());
    doNothing().when(statsService).resetStats();

    mockMvc.perform(post("/admin/statistics")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/statistics"))
            .andExpect(flash().attributeExists("message"));

    verify(statsService).saveSnapshot(stats);
    verify(statsService).resetStats();
  }

  //============================
  // SAVE SNAPSHOT - FORBIDDEN
  //============================

  @Test
  @WithMockUser(roles = "USER")
  void saveStatsSnapshot_shouldReturn403_whenNotAdmin() throws Exception {

    mockMvc.perform(post("/admin/statistics")
            .with(csrf()))
            .andExpect(status().isForbidden());
  }

  //============================
  //      GET HISTORY
  //============================

  @Test
  @WithMockUser(roles = "ADMIN")
  void history_shouldReturnView() throws Exception {

    when(statsService.getAllSnapshots())
            .thenReturn(List.of());

    mockMvc.perform(get("/admin/history"))
            .andExpect(status().isOk())
            .andExpect(view().name("/admin/history"))
            .andExpect(model().attributeExists("snapshots"));
  }

  //============================
  //   GET SNAPSHOT DETAILS
  //============================

  @Test
  @WithMockUser(roles = "ADMIN")
  void snapshotDetails_shouldReturnView() throws Exception {

    Long id = 1L;

    when(statsService.getSnapshotViewById(id))
            .thenReturn(new StatsViewModel());

    mockMvc.perform(get("/admin/history/{id}", id))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/snapshot-details"))
            .andExpect(model().attributeExists("stats"));
  }

  //============================
  // GET SELLERS PERFORMANCE
  //============================

  @Test
  @WithMockUser(roles = "ADMIN")
  void sellersPerformance_shouldReturnView() throws Exception {

    TopSellerViewModel seller = mock(TopSellerViewModel.class);

    when(seller.getFirstName()).thenReturn("John");
    when(seller.getLastName()).thenReturn("Doe");
    when(seller.getEmail()).thenReturn("john@test.com");
    when(seller.getPhoneNumber()).thenReturn("123456");
    when(seller.getSoldCount()).thenReturn(5L);

    Page<TopSellerViewModel> page =
            new PageImpl<>(List.of(seller));

    when(offerService.getSellerPerformanceByYear(anyInt(), anyInt()))
            .thenReturn(page);

    mockMvc.perform(get("/admin/sellers-performance")
            .param("year", "2024")
            .param("page", "0"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sellers-performance"))
            .andExpect(model().attributeExists("sellers"))
            .andExpect(model().attributeExists("years"));
  }

  //===========================================
  // GET SELLERS PERFORMANCE WITH DEFAULT YEAR
  //===========================================

  @Test
  @WithMockUser(roles = "ADMIN")
  void sellersPerformance_shouldUseCurrentYear_whenNotProvided() throws Exception {

    when(offerService.getSellerPerformanceByYear(anyInt(), anyInt()))
            .thenReturn(new PageImpl<>(List.of()));

    mockMvc.perform(get("/admin/sellers-performance"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("selectedYear"));
  }

  //===============================
  // GET SOLD CARS STATS - SUCCESS
  //===============================

  @Test
  @WithMockUser(roles = "ADMIN")
  void soldCarsStats_shouldReturnView() throws Exception {

    Page<SoldOfferViewModel> page =
            new PageImpl<>(List.of(new SoldOfferViewModel()));

    when(soldOfferService.getSoldCarsByYear(anyInt(), anyInt()))
            .thenReturn(page);

    mockMvc.perform(get("/admin/sold-cars-stats")
            .param("year", "2024"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sold-cars-year"))
            .andExpect(model().attributeExists("cars"))
            .andExpect(model().attributeExists("years"));
  }

  //=================================
  // GET SOLD CARS STATS - FORBIDDEN
  //=================================

  @Test
  @WithMockUser(roles = "USER")
  void sellersPerformance_shouldReturn403_whenNotAdmin() throws Exception {

    mockMvc.perform(get("/admin/sellers-performance"))
            .andExpect(status().isForbidden());
  }
}
