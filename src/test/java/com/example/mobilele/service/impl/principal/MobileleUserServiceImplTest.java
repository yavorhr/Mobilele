package com.example.mobilele.service.impl.principal;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class MobileleUserServiceImplTest {
  private UserRoleEntity adminRole, userRole;
  private UserEntity user;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private MobileleUserServiceImpl userService;

  @BeforeEach
  void init() {
    // Arrange
    user = new UserEntity();
    user.setId(1L);
    user.setUsername("user");
    user.setPassword("password");
    user.setAccountLocked(false);

    adminRole = new UserRoleEntity();
    userRole = new UserRoleEntity();

    adminRole.setRole(UserRoleEnum.ADMIN);
    userRole.setRole(UserRoleEnum.USER);

    user.setRoles(List.of(adminRole, userRole));
  }

  @Test
  public void testLoadUserByUsername_UserExists() {
    // Arrange
    Mockito.when(userRepository.findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    // Act
    var actualUser = userService.loadUserByUsername("user");

    // Assert
    Assertions.assertNotNull(actualUser);
    Assertions.assertEquals("user", actualUser.getUsername());
    Assertions.assertEquals("password", actualUser.getPassword());

    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = actualUser.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).collect(
                    Collectors.joining(", "));

    Assertions.assertEquals(actualUser.getUsername(), user.getUsername());
    Assertions.assertEquals(expectedRoles, actualRoles);
  }

 @Test
 public void testUserNotFound() {
   Mockito.when(userRepository.findByUsername("missingUser"))
           .thenReturn(Optional.empty());

    Assertions.assertThrows(
            UsernameNotFoundException.class,
            () -> userService.loadUserByUsername("missingUser")
    );
  }

}