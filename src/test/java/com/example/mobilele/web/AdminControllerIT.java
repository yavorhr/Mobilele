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
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;

  private UserEntity adminUser, user;

  private UserRoleEntity adminRole, userRole;

  @BeforeEach
  void setUp() {
    adminRole = new UserRoleEntity(UserRoleEnum.ADMIN);
    userRole = new UserRoleEntity(UserRoleEnum.USER);
    roleRepository.saveAll(List.of(adminRole, userRole));

    adminUser = new UserEntity();
    adminUser.setPassword("password");
    adminUser.setEnabled(true);
    adminUser.setUsername("admin");
    adminUser.setEmail("admin@abv.bg");
    adminUser.setFirstName("admin");
    adminUser.setLastName("adminov");
    adminUser.setRoles(List.of(adminRole, userRole));
    adminUser.setAccountLocked(false);

    user = new UserEntity();
    user.setPassword("password");
    user.setEnabled(true);
    user.setUsername("user");
    user.setEmail("user@abv.bg");
    user.setFirstName("user");
    user.setLastName("userov");
    user.setRoles(List.of(userRole));
    user.setAccountLocked(false);

    userRepository.saveAll(List.of(adminUser, user));
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
  }

}
