package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserRoleEntity;
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
  public UserRoleEntity findUserRole(UserRoleEnum userRole) {
    return this.userRoleRepository.findByRole(userRole);
  }

  @Override
  public void initRoles() {
    if (userRoleRepository.count() == 0) {
      UserRoleEntity userRoleEntity = new UserRoleEntity();
      userRoleEntity.setRole(UserRoleEnum.USER);

      UserRoleEntity adminRole = new UserRoleEntity();
      adminRole.setRole(UserRoleEnum.ADMIN);

      this.userRoleRepository.saveAll(List.of(userRoleEntity, adminRole));
    }
  }
}
