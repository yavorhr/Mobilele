package com.example.mobilele.web;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
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

  @Test
  @WithMockUser
  void getFindOffersView_shouldReturnCategoriesPage() throws Exception {

    mockMvc.perform(get("/offers/find"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers-categories"));
  }

  @Test
  void getSearchFormByCategory_shouldReturnFormWithVehicleType() throws Exception {

    mockMvc.perform(get("/offers/find/CAR"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers-find"))
            .andExpect(model().attributeExists("vehicleType"));
  }
}