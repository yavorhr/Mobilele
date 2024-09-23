package com.example.mobilele.init;

import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DBInit implements CommandLineRunner {
  private final UserService userService;
  private final UserRoleService  userRoleService;

  public DBInit(UserService userService, UserRoleService userRoleService) {
    this.userService = userService;
    this.userRoleService = userRoleService;
  }

  @Override
  public void run(String... args) throws Exception {
    userRoleService.initRoles();
    userService.initUsers();
  }
}
