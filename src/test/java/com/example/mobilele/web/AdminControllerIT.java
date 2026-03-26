package com.example.mobilele.web;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.repository.UserRoleRepository;
import com.example.mobilele.service.UserAdminService;
import com.example.mobilele.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAdminService userAdminService;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  void testViewNotifications_AdminAccess() throws Exception {

    UserAdministrationViewModel userVm = new UserAdministrationViewModel();
    userVm.setUsername("admin");
    userVm.setRoles(Set.of(UserRoleEnum.ADMIN));

    Page<UserAdministrationViewModel> page =
            new PageImpl<>(List.of(userVm), PageRequest.of(0, 6), 1);

    when(userAdminService.searchPaginatedUsersPerEmail(any(), any()))
            .thenReturn(page);

    mockMvc.perform(get("/admin/notifications"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/notifications"))
            .andExpect(model().attributeExists("usersPage", "users", "query"));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void testViewNotifications_Forbidden() throws Exception {

    mockMvc.perform(get("/admin/notifications"))
            .andExpect(status().isForbidden());
  }
}
