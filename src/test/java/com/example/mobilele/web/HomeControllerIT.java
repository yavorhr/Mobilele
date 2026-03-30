package com.example.mobilele.web;

import com.example.mobilele.model.view.feedback.FeedbackSummaryViewModel;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class HomeControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @MockBean
  private FeedbackService feedbackService;

  @MockBean
  private SoldOfferService soldOfferService;

  @Test
  void index_shouldReturnIndexViewWithModelAttributes() throws Exception {

    when(offerService.findLatestOffers(5)).thenReturn(List.of());
    when(offerService.findMostViewedOffers(5)).thenReturn(List.of());
    when(feedbackService.findRecentFeedbacks(10)).thenReturn(List.of());
    when(feedbackService.getFeedbackSummary()).thenReturn(new FeedbackSummaryViewModel());
    when(soldOfferService.getSoldVehiclesCount()).thenReturn(10L);

    mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists(
                    "latestOffers",
                    "mostViewedOffers",
                    "feedbacks",
                    "summary",
                    "startYear",
                    "soldCount"
            ))
            .andExpect(model().attribute("startYear", 2025))
            .andExpect(model().attribute("soldCount", 10L));
  }

  @Test
  @WithAnonymousUser
  void topSellers_shouldRedirectToLogin_whenNotAuthenticated() throws Exception {

    mockMvc.perform(get("/sellers/top"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  @WithMockUser
  void topSellers_shouldReturnTopSellersView_whenAuthenticated() throws Exception {

    when(offerService.getTop20Sellers()).thenReturn(List.of());

    mockMvc.perform(get("/sellers/top"))
            .andExpect(status().isOk())
            .andExpect(view().name("top-sellers"))
            .andExpect(model().attributeExists("topSellers"));
  }
}

