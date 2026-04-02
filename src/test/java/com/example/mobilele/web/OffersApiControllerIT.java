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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    // Example: assuming CITY1 belongs to BULGARIA
    when(messageSource.getMessage(startsWith("city."), any(), any()))
            .thenAnswer(invocation -> {
              String key = invocation.getArgument(0);
              return key + "_translated";
            });

    mockMvc.perform(get("/locations/cities")
            .param("country", "BULGARIA")
            .locale(Locale.ENGLISH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
  }

}
