package com.example.mobilele.service;

import com.example.mobilele.model.entity.UserRole;
import com.example.mobilele.model.entity.enums.UserRoleEnum;

public interface UserRoleService {
  void initRoles();

  UserRole findUserRole(UserRoleEnum userRole);
}
