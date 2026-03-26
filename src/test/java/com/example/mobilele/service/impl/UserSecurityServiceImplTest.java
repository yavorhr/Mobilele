package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserSecurityServiceImpl userSecurityService;

  @Test
  void increaseUserFailedLoginAttempts_shouldIncrementAndSave_whenBelowThreshold() {
    UserEntity user = new UserEntity();
    user.setFailedLoginAttempts(1);

    userSecurityService.increaseUserFailedLoginAttempts(user);

    assertEquals(2, user.getFailedLoginAttempts());
    verify(userRepository).save(user);
  }

  @Test
  void increaseUserFailedLoginAttempts_shouldLockAccount_whenReachesThree() {
    UserEntity user = new UserEntity();
    user.setFailedLoginAttempts(2);

    userSecurityService.increaseUserFailedLoginAttempts(user);

    assertTrue(user.isAccountLocked());
    assertEquals(0, user.getFailedLoginAttempts());
    assertNotNull(user.getLockTime());

    verify(userRepository).save(user);
  }

  @Test
  void lockAccount_shouldSetLockStateAndResetAttempts() {
    UserEntity user = new UserEntity();
    user.setFailedLoginAttempts(5);

    userSecurityService.lockAccount(user);

    assertTrue(user.isAccountLocked());
    assertEquals(0, user.getFailedLoginAttempts());
    assertNotNull(user.getLockTime());

    verify(userRepository).save(user);
  }
}