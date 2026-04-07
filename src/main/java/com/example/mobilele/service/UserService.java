package com.example.mobilele.service;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.user.UserViewModel;

import java.util.List;

public interface UserService {

  void seedUsers();

  void registerAndLoginUser(UserRegisterServiceModel serviceModel);

  UserEntity findByUsername(String username);

  UserEntity findById(Long id);

  void updateUser(UserEntity user);

  boolean isUserNameAvailable(String userName);

  List<UserEntity> findAll();

  UserViewModel findUserViewModelById(Long id);

  UserViewModel updateUserProfile(Long userId, UserEditBindingModel bindingModel);

  void deleteProfileById(Long userId);

  boolean isEmailAvailable(String email);

  boolean isPhoneNumberAvailable(String phoneNumber);

  boolean isOwnerOrIsAdmin(String username, Long routeId);

  boolean isNotOwnerOrIsAdmin(String username, Long routeId);

  boolean isOwner(String username, Long routeId);

  UserEntity getUserByUsernameOrThrow(String username);

  UserEntity getUserByIdOrThrow(Long id);

  UserEntity getUserByPhoneNumberOrThrow(String phoneNumber);


}
