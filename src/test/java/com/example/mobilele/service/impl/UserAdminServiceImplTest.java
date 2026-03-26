package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

  @Test
  void isNotModifyingOwnProfile_shouldReturnFalseWhenSameUser() {
    assertFalse(userAdminService.isNotModifyingOwnProfile("user", "user"));
  }

  @Test
  void isNotModifyingOwnProfile_shouldReturnTrueWhenDifferentUsers() {
    assertTrue(userAdminService.isNotModifyingOwnProfile("user1", "user2"));
  }

  @Test
  void updateUserRoles_shouldSetRolesCorrectly() {
    UserEntity user = new UserEntity();

    UserRoleEntity adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    when(userService.getUserByUsernameOrThrow("user")).thenReturn(user);
    when(roleService.findUserRole(UserRoleEnum.ADMIN)).thenReturn(adminRole);

    userAdminService.updateUserRoles("user", new String[]{"ADMIN"});

    assertEquals(1, user.getRoles().size());
    assertEquals(UserRoleEnum.ADMIN, user.getRoles().get(0).getRole());
  }
  @Test
  void searchPaginatedUsersPerEmail_shouldMapCorrectly() {
    UserEntity user = new UserEntity();
    user.setEmail("test@mail.com");

    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.USER);
    user.setRoles(List.of(role));

    Page<UserEntity> page = new PageImpl<>(List.of(user));

    when(userRepository.findByEmailContainingIgnoreCase(eq("test"), any()))
            .thenReturn(page);

    UserAdministrationViewModel vm = new UserAdministrationViewModel();

    when(modelMapper.map(user, UserAdministrationViewModel.class))
            .thenReturn(vm);

    Page<UserAdministrationViewModel> result =
            userAdminService.searchPaginatedUsersPerEmail("test", PageRequest.of(0, 10));

    assertEquals(1, result.getContent().size());
    assertTrue(result.getContent().get(0).getRoles().contains(UserRoleEnum.USER));
  }

  @Test
  void changeAccess_shouldDisableUser() {
    UserEntity user = new UserEntity();
    user.setEnabled(true);

    when(userService.getUserByUsernameOrThrow("user")).thenReturn(user);
    when(modelMapper.map(any(), eq(UserUpdateStatusResponse.class)))
            .thenReturn(new UserUpdateStatusResponse());

    userAdminService.changeAccess("user");

    assertFalse(user.isEnabled());
    verify(userRepository).save(user);
  }

  @Test
  void deleteUser_shouldClearRolesAndDelete() {
    UserEntity user = new UserEntity();

    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.USER);

    user.setRoles(new ArrayList<>(List.of(role)));

    when(userService.getUserByUsernameOrThrow("user")).thenReturn(user);

    userAdminService.deleteUser("user");

    assertTrue(user.getRoles().isEmpty());
    verify(userRepository).save(user);
    verify(userRepository).delete(user);
  }
}