package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.service.UserLoginServiceModel;
import com.example.mobilele.model.entity.User;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public boolean login(UserLoginServiceModel userLoginServiceModel) {

    Optional<User> user = this.userRepository.findByUsername(userLoginServiceModel.getUsername());

    if (user.isEmpty()) {
      return false;
    } else {
      return passwordEncoder.matches(userLoginServiceModel.getPassword(), user.get().getPassword());
    }
  }
}
