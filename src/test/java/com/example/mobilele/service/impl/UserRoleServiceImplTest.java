package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
}