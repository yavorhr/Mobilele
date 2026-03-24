package com.example.mobilele.service.impl.principal;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MobileleUserServiceImplTest {
  private UserRoleEntity adminRole, userRole;
  private UserEntity user;
  private UserDetails userDetails;

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
    when(userRepository
            .findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    // Act
     userDetails = userService.loadUserByUsername("user");

    // Assert
    assertNotNull(userDetails);
    assertEquals("user", userDetails.getUsername());
    assertEquals("password", userDetails.getPassword());

    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).collect(
                    Collectors.joining(", "));

    assertEquals(userDetails.getUsername(), user.getUsername());
    assertEquals(expectedRoles, actualRoles);
  }

 @Test
 public void testUserNotFound() {
   when(userRepository.findByUsername("missingUser"))
           .thenReturn(Optional.empty());

    assertThrows(
            UsernameNotFoundException.class,
            () -> userService.loadUserByUsername("missingUser")
    );
  }

  @Test
  void testMapToUserDetails() {
    // Arrange test getUserId
    List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    when(userRepository
            .findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    // Act
    userDetails = userService.loadUserByUsername("user");
    MobileleUser mobileleUser = new MobileleUser(user, authorities);

    // Assert
   assertEquals(1L, ((MobileleUser) userDetails).getId());
   assertEquals(user.getUsername(), userDetails.getUsername());
   assertEquals(user.getPassword(), userDetails.getPassword());
   assertTrue(userDetails.isEnabled());
   assertTrue(userDetails.isAccountNonExpired());
   assertTrue(userDetails.isCredentialsNonExpired());
   assertTrue(userDetails.isAccountNonLocked());
   assertEquals(2, userDetails.getAuthorities().size());
   assertEquals(mobileleUser.getUserEntity(), user);

  }
}