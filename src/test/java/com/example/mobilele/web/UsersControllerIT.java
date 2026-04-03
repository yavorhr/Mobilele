package com.example.mobilele.web;

import com.example.mobilele.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UsersControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private ModelMapper modelMapper;

  //==================
  // GET REGISTER
  //==================

  @Test
  void registerPage_shouldReturnView() throws Exception {

    mockMvc.perform(get("/users/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
  }

  //====================
  // POST REGISTER USER
  //====================

  @Test
  void register_shouldRedirectBack_whenInvalid() throws Exception {
    mockMvc.perform(post("/users/register")
            .param("username", "")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("register"))
            .andExpect(flash().attributeExists("userRegisterBindingModel"));
  }
}
