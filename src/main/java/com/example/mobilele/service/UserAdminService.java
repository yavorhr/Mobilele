package com.example.mobilele.service;

import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAdminService {

  void deleteUser(String username);

  UserUpdateStatusResponse changeAccess(String username);

  UserUpdateStatusResponse modifyLockStatus(String username);

  void updateUserRoles(String username, String[] roles);

  boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser);

  Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String emailQuery, Pageable pageable);
}
