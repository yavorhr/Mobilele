package com.example.mobilele.web;


import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "user1", roles = {"USER"})
public class FeedbacksControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FeedbackService feedbackService;

  UserEntity user;

  MobileleUser mobileleUser;

  Authentication auth;

  @BeforeEach
  void setup() {
    // 1. Create UserEntity
    user = new UserEntity();
    user.setId(1L);
    user.setUsername("user1");
    user.setPassword("password");
    user.setAccountLocked(false);

    List<GrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority("ROLE_USER"));

    mobileleUser = new MobileleUser(user, authorities);

    auth = new UsernamePasswordAuthenticationToken(
            mobileleUser,
            null,
            authorities
    );
  }

  @Test
  void submitFeedback_shouldReturnSuccess() throws Exception {
    mockMvc.perform(post("/users/submit-feedback")
            .with(authentication(auth))
            .param("rating", "5")
            .param("comment", "Great app!")
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Thank you for your feedback!"));
  }

  @Test
  void submitFeedback_shouldFail_whenRatingIsZero() throws Exception {
    mockMvc.perform(post("/users/submit-feedback")
            .with(authentication(auth))
            .param("rating", "0")
            .param("comment", "Nice app")
            .with(csrf()))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("Please select at least one star."));
  }
}
