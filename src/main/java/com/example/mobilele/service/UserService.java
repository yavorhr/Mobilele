package com.example.mobilele.service;

import com.example.mobilele.model.dto.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;


public interface UserService {

  void initUsers();

  void registerAndLoginUser(UserRegisterServiceModel serviceModel);

  UserEntity findByUsername(String username);

  UserEntity findById(Long id);

  boolean isUserNameAvailable(String userName);
}
