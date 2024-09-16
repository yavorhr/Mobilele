package com.example.mobilele.init;

import com.example.mobilele.model.entity.Brand;
import com.example.mobilele.model.entity.Model;
import com.example.mobilele.model.entity.User;
import com.example.mobilele.model.entity.enums.CategoryTypeEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {
  private final ModelRepository modelRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public DBInit(ModelRepository modelRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.modelRepository = modelRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    initCarModel();
    initUser();
  }

  private void initUser() {
    if (this.userRepository.count() == 0) {
      User user = createUser();
      this.userRepository.save(user);
    }
  }

  private void initCarModel() {
    if (this.modelRepository.count() == 0) {
      Brand brand = new Brand();
      brand.setName("BMW");

      Model x1 = new Model();
      x1.setBrand(brand);
      x1.setCategory(CategoryTypeEnum.CAR);
      x1.setName("x5");
      x1.setStartYear(2010);
      x1.setImageUrl("https://www.bmw.bg/bg/all-models/m-series/bmw-x1-m35i/2023/bmw-x1-m35i-overview.html");

      this.modelRepository.save(x1);
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
