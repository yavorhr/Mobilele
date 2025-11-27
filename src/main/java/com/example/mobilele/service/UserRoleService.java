package com.example.mobilele.service;

import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;

public interface UserRoleService {
  void initRoles();

  UserRoleEntity findUserRole(UserRoleEnum userRole);
  ;
}
