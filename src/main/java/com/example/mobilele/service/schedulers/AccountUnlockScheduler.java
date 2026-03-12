package com.example.mobilele.service.schedulers;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.service.UserSecurityService;
import com.example.mobilele.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class AccountUnlockScheduler {
  private final UserService userService;
  private final UserSecurityService userSecurityService;

  public AccountUnlockScheduler(UserService userService, UserSecurityService userSecurityService) {
    this.userService = userService;
    this.userSecurityService = userSecurityService;
  }

  //every 15 minutes
  @Scheduled(fixedRate = 900000)
  public void unlockExpiredAccounts() {
    List<UserEntity> lockedUsers = userSecurityService.findLockedUsers();

    LocalDateTime now = LocalDateTime.now();

    for (UserEntity user : lockedUsers) {
      if (user.getLockTime() != null && user.getLockTime().isBefore(now)) {
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        userService.updateUser(user);
      }
    }

  }
}
