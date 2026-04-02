package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
  // GET CITIES
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

  // =========================
  // INVALID COUNTRY
  // =========================

  @Test
  void getCities_shouldReturn400_whenInvalidCountry() throws Exception {
    mockMvc.perform(get("/locations/cities")
            .param("country", "INVALID"))
            .andExpect(status().isBadRequest())
            .andDo(print());
  }

  @Test
  void toggleReservation_shouldReturnNewStatus() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(), eq(offerId)))
            .thenReturn(true);

    when(favoritesService.toggleReservation(offerId, "user1"))
            .thenReturn(true);

    mockMvc.perform(patch("/offers/{id}/toggle-reservation", offerId)
            .with(authentication(createAuth("user1")))
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reserved").value(true));
  }

  // =========================
  // FORBIDDEN (SECURITY FAIL)
  // =========================

  @Test
  void toggleReservation_shouldReturn403_whenSecurityFails() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(), eq(offerId)))
            .thenReturn(false);

    mockMvc.perform(patch("/offers/{id}/toggle-reservation", offerId)
            .with(authentication(createAuth("user1")))
            .with(csrf()))
            .andExpect(status().isForbidden());
  }

  // =========================
  // UNAUTHORIZED
  // =========================

  @Test
  @WithAnonymousUser
  void toggleReservation_shouldRedirectToLogin_whenNotAuthenticated() throws Exception {

    mockMvc.perform(patch("/offers/{id}/toggle-reservation", 1L)
            .with(csrf()))
            .andExpect(status().isFound()); // 302
  }

  private Authentication createAuth(String username) {

    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setUsername(username);
    user.setPassword("password");
    user.setAccountLocked(false);

    List<GrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority("ROLE_USER"));

    MobileleUser mobileleUser = new MobileleUser(user, authorities);

    return new UsernamePasswordAuthenticationToken(
            mobileleUser,
            null,
            authorities
    );
  }
}
