package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.service.FavoritesService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OffersApiControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MessageSource messageSource;

  @MockBean
  private FavoritesService favoritesService;

  @MockBean(name = "security")
  private SecurityService securityService;

  // =========================
  // 🔹 GET CITIES
  // =========================

  @Test
  void getCities_shouldReturnFilteredCitiesWithLocalizedNames() throws Exception {

    when(messageSource.getMessage(startsWith("city."), any(Object[].class), any(Locale.class)))
            .thenAnswer(invocation -> {
              String key = invocation.getArgument(0);
              return key + "_translated";
            });

    mockMvc.perform(get("/locations/cities")
            .param("country", "Bulgaria")
            .locale(Locale.ENGLISH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getCities_shouldReturn400_whenInvalidCountry() throws Exception {
    mockMvc.perform(get("/locations/cities")
            .param("country", "INVALID"))
            .andExpect(status().isBadRequest())
            .andDo(print());
  }
}
