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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    Mockito.when(userRepository
            .findByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));
  }

  @Test
  public void testLoadUserByUsername_UserExists() {
    // Act
     userDetails = userService.loadUserByUsername("user");

    // Assert
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals("user", userDetails.getUsername());
    Assertions.assertEquals("password", userDetails.getPassword());

    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).collect(
                    Collectors.joining(", "));

    Assertions.assertEquals(userDetails.getUsername(), user.getUsername());
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

  @Test
  void testMapToUserDetails() {
    // Arrange test getUserId
    List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    // Act
    userDetails = userService.loadUserByUsername("user");
    MobileleUser mobileleUser = new MobileleUser(user, authorities);

    // Assert
    Assertions.assertEquals(1L, ((MobileleUser) userDetails).getId());
    Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
    Assertions.assertEquals(user.getPassword(), userDetails.getPassword());
    Assertions.assertTrue(userDetails.isEnabled());
    Assertions.assertTrue(userDetails.isAccountNonExpired());
    Assertions.assertTrue(userDetails.isCredentialsNonExpired());
    Assertions.assertTrue(userDetails.isAccountNonLocked());
    Assertions.assertEquals(2, userDetails.getAuthorities().size());
    Assertions.assertEquals(mobileleUser.getUserEntity(), user);
  }

}