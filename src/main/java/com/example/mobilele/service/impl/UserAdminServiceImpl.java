package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserAdminService;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
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

  public UserAdminServiceImpl(ModelMapper modelMapper, UserRepository userRepository, UserRoleService roleService) {
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
    this.roleService = roleService;
  }

  @Override
  public UserUpdateStatusResponse modifyLockStatus(String username) {
    UserEntity userEntity = this.getByUsernameOrThrow(username);

    if (userEntity.isAccountLocked()) {
      userEntity.setAccountLocked(false);
    } else {
      userEntity.setAccountLocked(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser) {
    return !loggedInUser.equals(targetUser);
  }

  @Override
  @Transactional
  public void updateUserRoles(String username, String[] roles) {
    UserEntity userEntity = this.getByUsernameOrThrow(username);

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
    UserEntity userEntity = this.getByUsernameOrThrow(username);

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
    UserEntity userEntity = getByUsernameOrThrow(username);

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);
    this.userRepository.delete(userEntity);
  }

  // Helpers
  private UserEntity getByUsernameOrThrow(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with username: " + username + " not found"));
  }

  private UserEntity getByIdOrThrow(Long userId) {
    return userRepository.findById(userId)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with id: " + userId + " does not exist!"));
  }

}
