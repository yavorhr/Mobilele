package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRoleRepository;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {

  @Mock
  private UserRoleRepository userRoleRepository;

  @InjectMocks
  private UserRoleServiceImpl userRoleService;

  @Test
  void findUserRole_shouldReturnRole_whenExists() {
    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.ADMIN);

    when(userRoleRepository.findByRole(UserRoleEnum.ADMIN))
            .thenReturn(Optional.of(role));

    UserRoleEntity result = userRoleService.findUserRole(UserRoleEnum.ADMIN);

    assertEquals(UserRoleEnum.ADMIN, result.getRole());
  }

  @Test
  void findUserRole_shouldThrow_whenNotFound() {
    when(userRoleRepository.findByRole(UserRoleEnum.ADMIN))
            .thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> userRoleService.findUserRole(UserRoleEnum.ADMIN));
  }

  @Test
  void seedRoles_shouldSeed_whenRepositoryIsEmpty() {
    when(userRoleRepository.count()).thenReturn(0L);

    userRoleService.seedRoles();

    verify(userRoleRepository).saveAll(argThat(iterable -> {
      List<UserRoleEnum> roles = new ArrayList<>();
      iterable.forEach(r -> roles.add(r.getRole()));

      return roles.contains(UserRoleEnum.USER)
              && roles.contains(UserRoleEnum.ADMIN)
              && roles.size() == 2;
    }));
  }

  @Test
  void seedRoles_shouldNotSeed_whenRepositoryNotEmpty() {
    when(userRoleRepository.count()).thenReturn(5L);

    userRoleService.seedRoles();

    verify(userRoleRepository, never()).saveAll(any());
  }
}