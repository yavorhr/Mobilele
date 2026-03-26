package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserSecurityService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
  private final UserRepository userRepository;

  public UserSecurityServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void increaseUserFailedLoginAttempts(UserEntity user) {
    int failedAttempts = user.getFailedLoginAttempts() + 1;
    user.setFailedLoginAttempts(failedAttempts);

    if (user.getFailedLoginAttempts() == 3) {
      this.lockAccount(user);
    } else {
      userRepository.save(user);
    }
  }

  @Override
  public void lockAccount(UserEntity user) {
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().plusMinutes(15));
    user.setFailedLoginAttempts(0);

    userRepository.save(user);
  }

  @Override
  public void resetFailedAttempts(UserEntity user) {
    user.setFailedLoginAttempts(0);
    this.userRepository.save(user);
  }

  @Override
  public List<UserEntity> findLockedUsers() {
    return this.userRepository.findAllLockedUsers();
  }
}
