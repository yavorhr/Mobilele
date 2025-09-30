package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserRoleService roleService;
  private final MobileleUserServiceImpl mobileleUserService;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleService roleService, MobileleUserServiceImpl mobileleUserService) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.mobileleUserService = mobileleUserService;
  }

  @Override
  public void registerAndLoginUser(UserRegisterServiceModel serviceModel) {
    UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

    UserEntity newUserEntity = new UserEntity();

    newUserEntity.
            setUsername(serviceModel.getUsername()).
            setFirstName(serviceModel.getFirstName()).
            setLastName(serviceModel.getLastName()).
            setPassword(passwordEncoder.encode(serviceModel.getPassword())).
            setRoles(List.of(userRoleEntity));

    newUserEntity = userRepository.save(newUserEntity);

    UserDetails principal = mobileleUserService.loadUserByUsername(newUserEntity.getUsername());

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            principal,
            newUserEntity.getPassword(),
            principal.getAuthorities()
    );

    SecurityContextHolder.
            getContext().
            setAuthentication(authentication);
  }

  @Override
  public UserEntity findByUsername(String username) {
    return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User with username" + username + "does not exist!"));
  }

  @Override
  public UserEntity findById(Long id) {
    return this.userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("User with id" + id + "does not exist!"));
  }

  @Override
  public void updateUser(UserEntity user) {
    this.userRepository.save(user);
  }

  @Override
  public boolean isUserNameAvailable(String username) {
    return userRepository.findByUsernameIgnoreCase(username).isEmpty();
  }

  @Override
  public void increaseUserFailedLoginAttempts(UserEntity user) {
    int failedAttempts = user.getFailedLoginAttempts() + 1;
    user.setFailedLoginAttempts(failedAttempts);

    if (user.getFailedLoginAttempts() == 3) {
      this.lockAccount(user);
    } else {
      userRepository.save(user);
    }
  }

  @Override
  public List<UserEntity> findLockedUsers() {
    return this.userRepository.findAllLockedUsers();
  }


  @Override
  public void lockAccount(UserEntity user) {
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().plusMinutes(15));
    user.setFailedLoginAttempts(0);

    userRepository.save(user);
  }

  @Override
  public void resetFailedAttempts(UserEntity user) {
    user.setFailedLoginAttempts(0);
    this.userRepository.save(user);
  }

  @Override
  public void initUsers() {
    if (userRepository.count() == 0) {
      UserRoleEntity adminRole = this.roleService.findUserRole(UserRoleEnum.ADMIN);
      UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

      UserEntity admin = createUser("admin", "John", "Atanasoff",   "test");
      admin.setRoles(List.of(adminRole, userRoleEntity));

      UserEntity userEntity = createUser("user", "Petar", "Ivanov",   "test");
      userEntity.setRoles(List.of(userRoleEntity));

      this.userRepository.saveAll(List.of(admin, userEntity));
    }
  }

  // Helpers
  private UserEntity createUser(String username, String firstName, String lastName, String password) {
    UserEntity userEntity = new UserEntity();

    userEntity
            .setUsername(username)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPassword(passwordEncoder.encode(password));

    return userEntity;
  }
}
