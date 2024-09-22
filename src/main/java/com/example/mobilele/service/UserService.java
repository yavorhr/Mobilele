package com.example.mobilele.service;
import com.example.mobilele.model.dto.service.UserLoginServiceModel;

public interface UserService {

  boolean login (UserLoginServiceModel userLoginServiceModel);

  void logout();
}
