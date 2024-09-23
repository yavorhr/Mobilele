package com.example.mobilele.init;

import com.example.mobilele.model.entity.Brand;
import com.example.mobilele.model.entity.Model;
import com.example.mobilele.model.entity.User;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  public DBInit(ModelRepository modelRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
    this.modelRepository = modelRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  @Override
  public void run(String... args) throws Exception {
    initUser();
  }

  private void initUser() {
    if (this.userRepository.count() == 0) {
      User user = createUser();
      this.userRepository.save(user);
    }
  }

  // Helpers
  private User createUser() {
    User user = new User();
    user.setActive(true);
    user.setUsername("admin");
    user.setImageUrl("emptyUrl");
    user.setFirstName("admin");
    user.setLastName("adminov");
    user.setPassword(passwordEncoder.encode("test"));

    return user;
  }
}
