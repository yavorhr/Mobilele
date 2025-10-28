package com.example.mobilele.service;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.user.UserViewModel;

import java.util.List;


public interface UserService {

  void initUsers();

  void registerAndLoginUser(UserRegisterServiceModel serviceModel);

  UserEntity findByUsername(String username);

  UserEntity findById(Long id);

  void updateUser(UserEntity user);

  boolean isUserNameAvailable(String userName);

  void increaseUserFailedLoginAttempts(UserEntity user);

  void lockAccount(UserEntity user);

  void resetFailedAttempts(UserEntity user);

  List<UserEntity> findLockedUsers();

  UserViewModel findUserViewModelById(Long id);

  UserViewModel updateUserProfile(Long userId, UserEditBindingModel bindingModel);
}
