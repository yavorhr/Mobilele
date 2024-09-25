package com.example.mobilele.service;
import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.model.dto.service.UserRegisterServiceModel;

public interface UserService {

  boolean login (UserLoginServiceModel userLoginServiceModel);

  void logout();

  void initUsers();

  void registerAndLoginUser(UserRegisterServiceModel serviceModel);
}
