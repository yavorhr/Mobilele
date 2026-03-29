package com.example.mobilele.web;

import com.example.mobilele.model.binding.RoleUpdateRequest;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.repository.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin", roles = {"ADMIN"})
class AdminControllerIT {
  private UserEntity adminUser;
  private UserEntity targetUser;
  private UserRoleEntity adminRole;
  private UserRoleEntity userRole;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository roleRepository;

  @BeforeEach
  void setUp() {
    // roles
    adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    roleRepository.saveAll(List.of(adminRole, userRole));

    adminUser = new UserEntity();
    adminUser.setUsername("admin");
    adminUser.setEmail("admin@abv.bg");
    adminUser.setPassword("password");
    adminUser.setPhoneNumber("222222222");
    adminUser.setEnabled(true);
    adminUser.setRoles(new ArrayList<>(List.of(adminRole, userRole)));
    adminUser.setAccountLocked(false);

    targetUser = new UserEntity();
    targetUser.setUsername("user1");
    targetUser.setEmail("user1@abv.bg");
    targetUser.setPhoneNumber("111111111");
    targetUser.setPassword("password");
    targetUser.setEnabled(true);
    targetUser.setRoles(new ArrayList<>(List.of(userRole)));
    targetUser.setAccountLocked(false);

    userRepository.saveAll(List.of(adminUser, targetUser));
    userRepository.flush();
  }

// =========================
  // 🔹 VIEW NOTIFICATIONS
  // =========================

  @Test
  void viewNotifications_shouldReturnPage() throws Exception {
    mockMvc.perform(get("/admin/notifications")
            .param("query", "")
            .param("page", "0")
            .param("size", "6"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/notifications"))
            .andExpect(model().attributeExists(
                    "usersPage",
                    "users",
                    "loggedInUsername",
                    "query",
                    "currentPage",
                    "totalPages"
            ));
  }

  @Test
  void changeUserAccess_shouldReturnUpdatedStatus() throws Exception {

    mockMvc.perform(put("/admin/api/change-user-access/{username}", targetUser.getUsername())
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("user1@abv.bg"))
            .andExpect(jsonPath("$.enabled").value(false))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void updateRoles_shouldUpdateAndReturnSuccessMessage() throws Exception {
    RoleUpdateRequest request = new RoleUpdateRequest();
    request.setUsername(targetUser.getUsername());
    request.setRoles(new String[]{"USER", "ADMIN"});

    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc.perform(patch("/admin/api/update-roles")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(content().string("Roles updated successfully"))
            .andDo(print());
  }

  @Test
  void changeUserLockStatus_shouldReturnStatusResponse() throws Exception {
    UserUpdateStatusResponse response = new UserUpdateStatusResponse();
    response.setUsername(targetUser.getUsername());
    response.setAccountLocked(false);
    response.setEnabled(targetUser.isEnabled());
    response.setRoles(targetUser.getRoles().stream().map(UserRoleEntity::getRole).collect(Collectors.toSet()));

    String json = new ObjectMapper().writeValueAsString(response);

    mockMvc.perform(put("/admin/api/change-user-lock-status/{username}",targetUser.getUsername())
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("user1@abv.bg"))
            .andExpect(jsonPath("$.accountLocked").value(true))
            .andDo(print());
  }
}
