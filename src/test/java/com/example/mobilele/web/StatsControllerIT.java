package com.example.mobilele.web;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import com.example.mobilele.service.StatsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
}
