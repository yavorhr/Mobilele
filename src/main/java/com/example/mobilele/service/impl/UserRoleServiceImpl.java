package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserRole;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRoleRepository;
import com.example.mobilele.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
  private final UserRoleRepository userRoleRepository;

  public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public void initRoles() {
    if (userRoleRepository.count() == 0) {
      UserRole userRole = new UserRole();
      userRole.setRole(UserRoleEnum.USER);

      UserRole adminRole = new UserRole();
      adminRole.setRole(UserRoleEnum.ADMIN);

      this.userRoleRepository.saveAll(List.of(userRole, adminRole));
    }
  }

  @Override
  public UserRole findUserRole(UserRoleEnum userRole) {
    return this.userRoleRepository.findByRole(userRole);
  }
}
