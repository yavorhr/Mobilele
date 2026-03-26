package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private OfferRepository offerRepository;

  @Mock
  private UserRoleService roleService;

  @Mock
  private MobileleUserServiceImpl mobileleUserService;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private SoldOfferRepository soldOfferRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @AfterEach
  void clearSecurity() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void registerAndLoginUser_shouldRegisterAndAuthenticate() {
    UserRegisterServiceModel serviceModel = new UserRegisterServiceModel();
    serviceModel.setUsername("user");
    serviceModel.setPassword("123");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("user");

    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.USER);

    when(roleService.findUserRole(UserRoleEnum.USER)).thenReturn(role);
    when(modelMapper.map(serviceModel, UserEntity.class)).thenReturn(userEntity);
    when(passwordEncoder.encode("123")).thenReturn("encoded");
    when(userRepository.save(any())).thenReturn(userEntity);

    UserDetails userDetails = mock(UserDetails.class);
    when(mobileleUserService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.getAuthorities()).thenReturn(List.of());

    userService.registerAndLoginUser(serviceModel);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void isUserNameAvailable_shouldReturnTrue_whenNotExists() {
    when(userRepository.findByUsernameIgnoreCase("user"))
            .thenReturn(Optional.empty());

    assertTrue(userService.isUserNameAvailable("user"));
  }
}
