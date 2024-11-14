package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserRoleService roleService;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleService roleService) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleService = roleService;
  }

  @Override
  public void registerAndLoginUser(UserRegisterServiceModel serviceModel) {
    UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

    UserEntity newUserEntity = new UserEntity();

    newUserEntity.
            setUsername(serviceModel.getUsername()).
            setFirstName(serviceModel.getFirstName()).
            setLastName(serviceModel.getLastName()).
            setActive(true).
            setPassword(passwordEncoder.encode(serviceModel.getPassword())).
            setRoles(List.of(userRoleEntity));

    newUserEntity = userRepository.save(newUserEntity);
    //TODO : register user
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public UserEntity findById(Long id) {
    return this.userRepository.findById(id).get();
  }

  @Override
  public boolean isUserNameAvailable(String username) {
    return userRepository.findByUsernameIgnoreCase(username).isEmpty();

  }

  @Override
  public void initUsers() {
    if (userRepository.count() == 0) {
      UserRoleEntity adminRole = this.roleService.findUserRole(UserRoleEnum.ADMIN);
      UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

      UserEntity admin = createUser("admin", "admin", "adminov", "noUrl", true, "test");
      admin.setRoles(List.of(adminRole, userRoleEntity));

      UserEntity userEntity = createUser("pesho", "Petar", "Ivanov", "n/a", true, "123");
      userEntity.setRoles(List.of(userRoleEntity));

      this.userRepository.saveAll(List.of(admin, userEntity));
    }
  }

  // Helpers
  private UserEntity createUser(String username, String firstName, String lastName, String imageUrl, boolean isActive, String password) {
    UserEntity userEntity = new UserEntity();

    userEntity
            .setActive(isActive)
            .setUsername(username)
            .setImageUrl(imageUrl)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPassword(passwordEncoder.encode(password));

    return userEntity;
  }

}
