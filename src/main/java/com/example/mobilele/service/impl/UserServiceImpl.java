package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.model.entity.User;
import com.example.mobilele.model.entity.UserRole;
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

    Optional<User> loggedInUserOpt =
            this.userRepository.findByUsername(userLoginServiceModel.getUsername());

    if (loggedInUserOpt.isEmpty()) {
      logout();
      return false;
    } else {
      boolean success = passwordEncoder.matches(
              userLoginServiceModel.getPassword(),
              loggedInUserOpt.get().getPassword());

      if (success) {
        User loggedInUser = loggedInUserOpt.get();

        currentUser.setUsername(loggedInUser.getUsername());
        currentUser.setLoggedIn(true);
        currentUser.setFirstName(loggedInUser.getFirstName());
        currentUser.setLastName(loggedInUser.getLastName());

        loggedInUser.getRoles().forEach(r-> currentUser.addRole(r.getRole()));
      }

      return success;
    }
  }

  @Override
  public void logout() {
    this.currentUser.clean();
  }

  @Override
  public void initUsers() {
    if (userRepository.count() == 0) {
      UserRole adminRole = this.roleService.findUserRole(UserRoleEnum.ADMIN);
      UserRole userRole = this.roleService.findUserRole(UserRoleEnum.USER);

      User admin = createUser("admin", "admin", "adminov", "noUrl", true, "test");
      admin.setRoles(List.of(adminRole, userRole));

      User user = createUser("pesho", "Petar", "Ivanov", "n/a", true, "123");
      user.setRoles(List.of(userRole));

      this.userRepository.saveAll(List.of(admin, user));
    }
  }

  // Helpers
  private User createUser(String username, String firstName, String lastName, String imageUrl, boolean isActive, String password) {
    User user = new User();
    user.setActive(isActive);
    user.setUsername(username);
    user.setImageUrl(imageUrl);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(passwordEncoder.encode(password));

    return user;
  }
}
