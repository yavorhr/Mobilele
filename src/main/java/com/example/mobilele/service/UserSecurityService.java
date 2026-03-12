package com.example.mobilele.service;

import com.example.mobilele.model.entity.UserEntity;

import java.util.List;

public interface UserSecurityService {

  void increaseUserFailedLoginAttempts(UserEntity user);

  void lockAccount(UserEntity user);

  void resetFailedAttempts(UserEntity user);

  List<UserEntity> findLockedUsers();

}
