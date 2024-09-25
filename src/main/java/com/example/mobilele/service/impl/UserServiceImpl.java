package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.model.dto.service.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final CurrentUser currentUser;
  private final UserRoleService roleService;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CurrentUser currentUser, UserRoleService roleService) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.currentUser = currentUser;
    this.roleService = roleService;
  }

  @Override
  public boolean login(UserLoginServiceModel userLoginServiceModel) {

    Optional<UserEntity> loggedInUserOpt =
            this.userRepository.findByUsername(userLoginServiceModel.getUsername());

    if (loggedInUserOpt.isEmpty()) {
      logout();
      return false;
    } else {
      boolean success = passwordEncoder.matches(
              userLoginServiceModel.getPassword(),
              loggedInUserOpt.get().getPassword());

      if (success) {
        UserEntity loggedInUserEntity = loggedInUserOpt.get();
        saveUserToSession(loggedInUserEntity);

        loggedInUserEntity.getRoles().forEach(r -> currentUser.addRole(r.getRole()));
      }
      return success;
    }
  }

  @Override
  public void logout() {
    this.currentUser.clean();
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

    saveUserToSession(newUserEntity);
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    return userRepository.findByUsername(username);
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

    userEntity.setActive(isActive)
            .setUsername(username)
            .setImageUrl(imageUrl)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPassword(passwordEncoder.encode(password));

    return userEntity;
  }

  private void saveUserToSession(UserEntity loggedInUserEntity) {
    currentUser.setUsername(loggedInUserEntity.getUsername());
    currentUser.setLoggedIn(true);
    currentUser.setFirstName(loggedInUserEntity.getFirstName());
    currentUser.setLastName(loggedInUserEntity.getLastName());
  }
}
