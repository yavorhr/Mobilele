package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.model.entity.User;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final CurrentUser currentUser;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CurrentUser currentUser) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.currentUser = currentUser;
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
      }

      return success;
    }
  }

  @Override
  public void logout() {
    this.currentUser.clean();
  }

}
