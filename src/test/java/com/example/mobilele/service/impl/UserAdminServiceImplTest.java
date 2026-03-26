package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdminServiceImplTest {

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserRoleService roleService;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserAdminServiceImpl userAdminService;

  @Test
  void modifyLockStatus_shouldToggleLock() {
    UserEntity user = new UserEntity();
    user.setAccountLocked(false);

    when(userService.getUserByUsernameOrThrow("user")).thenReturn(user);
    when(modelMapper.map(any(), eq(UserUpdateStatusResponse.class)))
            .thenReturn(new UserUpdateStatusResponse());

    userAdminService.modifyLockStatus("user");

    assertTrue(user.isAccountLocked());
    verify(userRepository).save(user);
  }

  @Test
  void modifyLockStatus_shouldUnlockIfLocked() {
    UserEntity user = new UserEntity();
    user.setAccountLocked(true);

    when(userService.getUserByUsernameOrThrow("user")).thenReturn(user);
    when(modelMapper.map(any(), eq(UserUpdateStatusResponse.class)))
            .thenReturn(new UserUpdateStatusResponse());

    userAdminService.modifyLockStatus("user");

    assertFalse(user.isAccountLocked());
  }

}