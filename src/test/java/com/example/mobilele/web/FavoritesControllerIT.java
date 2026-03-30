package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.service.FavoritesService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "user1", roles = {"USER"})
public class FavoritesControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FavoritesService favoritesService;

  @MockBean(name = "security")
  private SecurityService securityService;

  @BeforeEach
  void setUp() {
    when(securityService.canToggleFavorite(any(), anyLong()))
            .thenReturn(true);
  }

  @Test
  void toggleFavorite_shouldAddFavorite() throws Exception {
    Long offerId = 1L;

    when(favoritesService.toggleFavorite("user1", offerId))
            .thenReturn(true);

    mockMvc.perform(post("/users/favorites/{offerId}/toggle", offerId)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Offer added"));
  }

  @Test
  void toggleFavorite_shouldRemoveFavorite() throws Exception {
    Long offerId = 1L;

    when(favoritesService.toggleFavorite("user1", offerId))
            .thenReturn(false);

    mockMvc.perform(post("/users/favorites/{offerId}/toggle", offerId)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Offer removed"));
  }


}
