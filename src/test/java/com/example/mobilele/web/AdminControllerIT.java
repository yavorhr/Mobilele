package com.example.mobilele.web;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.repository.UserRoleRepository;
import com.example.mobilele.service.UserAdminService;
import com.example.mobilele.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    user.setUsername("admin");
    user.setEmail("admin@abv.bg");
    user.setFirstName("admin");
    user.setLastName("adminov");
    user.setRoles(List.of(adminRole, userRole));
    user.setAccountLocked(false);

    userRepository.saveAll(List.of(adminUser, user));
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
  }
}
