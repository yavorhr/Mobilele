package com.example.mobilele.web;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

  @MockBean
  private UserRepository userRepository;

  //==================
  // GET REGISTER
  //==================

  @Test
  void registerPage_shouldReturnView() throws Exception {

    mockMvc.perform(get("/users/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
  }

  //==============================
  // POST REGISTER USER - INVALID
  //==============================

  @Test
  void register_shouldRedirectBack_whenInvalid() throws Exception {
    mockMvc.perform(post("/users/register")
            .param("username", "")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("register"))
            .andExpect(flash().attributeExists("userRegisterBindingModel"));
  }

  //==============================
  // POST REGISTER USER - SUCCESS
  //==============================

  @Test
  void register_shouldRedirectHome_whenValid() throws Exception {

    when(modelMapper.map(any(), eq(UserRegisterServiceModel.class)))
            .thenReturn(new UserRegisterServiceModel());

    when(userService.isUserNameAvailable(any()))
            .thenReturn(true);

    when(userService.isPhoneNumberAvailable(any()))
            .thenReturn(true);

    when(userService.isEmailAvailable(any()))
            .thenReturn(true);

    mockMvc.perform(post("/users/register")
            .param("username", "user1")
            .param("email", "test333@test.com")
            .param("phoneNumber", "+359888222000")
            .param("firstName", "John22")
            .param("lastName", "Doe22")
            .param("password", "password123")
            .param("confirmPassword", "password123")
            .with(csrf()))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

    verify(userService).registerAndLoginUser(any());
  }

  //==============================
  //      GET LOGIN PAGE
  //==============================

  @Test
  void login_shouldReturnView() throws Exception {

    mockMvc.perform(get("/users/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
  }

  //==============================
  //  INVALID CREDENTIALS CASE
  //==============================

  @Test
  void login_shouldShowError_whenInvalidCredentials() throws Exception {

    mockMvc.perform(get("/users/login")
            .param("errorType", "INVALID_CREDENTIALS"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("login_error_message"));
  }

  //==============================
  //  UNKNOWN ERROR CASE
  //==============================

  @Test
  void login_shouldHandleUnknownErrorType() throws Exception {

    mockMvc.perform(get("/users/login")
            .param("errorType", "UNKNOWN"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("login_error_message", "An unknown error occurred."));
  }

  //==============================
  //  DELETE PROFILE - SUCCESS
  //==============================


  @Test
  void deleteProfile_shouldRedirectHome() throws Exception {

    mockMvc.perform(delete("/users/delete")
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

    verify(userService).deleteProfileById(1L);
  }

  //================================
  //  DELETE PROFILE - UNAUTHORIZED
  //================================

  @Test
  void deleteProfile_shouldRedirectToLogin_whenAnonymous() throws Exception {

    mockMvc.perform(delete("/users/delete")
            .with(csrf()))
            .andExpect(status().isFound()); // 302
  }

  @Test
  void showProfile_shouldReturnView() throws Exception {

    when(userService.findUserViewModelById(1L))
            .thenReturn(new UserViewModel());

    mockMvc.perform(get("/users/profile")
            .with(authentication(createAuth("user1"))))
            .andExpect(status().isOk())
            .andExpect(view().name("profile"))
            .andExpect(model().attributeExists("user"));
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
