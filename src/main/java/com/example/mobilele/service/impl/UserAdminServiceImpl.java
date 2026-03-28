package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserAdminService;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAdminServiceImpl implements UserAdminService {
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;
  private final UserRoleService roleService;
  private final UserService userService;

  public UserAdminServiceImpl(ModelMapper modelMapper, UserRepository userRepository, UserRoleService roleService, UserService userService) {
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.userService = userService;
  }

  @Override
  public UserUpdateStatusResponse modifyLockStatus(String username) {
    UserEntity userEntity = userService.getUserByUsernameOrThrow(username);

    if (userEntity.isAccountLocked()) {
      userEntity.setAccountLocked(false);
    } else {
      userEntity.setAccountLocked(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public boolean isNotModifyingOwnProfile(String targetUser, String loggedInUser) {
    return !loggedInUser.equals(targetUser);
  }

  @Override
  @Transactional
  public void updateUserRoles(String username, String[] roles) {
    UserEntity userEntity = userService.getUserByUsernameOrThrow(username);

    List<UserRoleEntity> userRoles =
            Arrays.stream(roles)
                    .map(r -> roleService.findUserRole(UserRoleEnum.valueOf(r)))
                    .toList();

    userEntity.setRoles(userRoles);
  }

  @Override
  public Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String email, Pageable pageable) {

    Page<UserEntity> usersPage =
            userRepository.findByEmailContainingIgnoreCase(email, pageable);

    return usersPage.map(u -> {
      UserAdministrationViewModel vm =
              modelMapper.map(u, UserAdministrationViewModel.class);

      Set<UserRoleEnum> roleEnums = u.getRoles().stream()
              .map(UserRoleEntity::getRole)
              .collect(Collectors.toSet());

      vm.setRoles(roleEnums);

      return vm;
    });
  }

  @Override
  public UserUpdateStatusResponse changeAccess(String username) {
    UserEntity userEntity = userService.getUserByUsernameOrThrow(username);

    if (userEntity.isEnabled()) {
      userEntity.setEnabled(false);
    } else {
      userEntity.setEnabled(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public void deleteUser(String username) {
    UserEntity userEntity = userService.getUserByUsernameOrThrow(username);

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);
    this.userRepository.delete(userEntity);
  }
}
