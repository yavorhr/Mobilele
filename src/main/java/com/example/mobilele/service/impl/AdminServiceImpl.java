package com.example.mobilele.service.impl;

import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.service.AdminService;
import com.example.mobilele.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
  private final UserService userService;

  public AdminServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String email, Pageable pageable) {
    List<UserAdministrationViewModel> users = userService.findUsersPerEmailInputIgnoreCase(email);

    int start = Math.toIntExact(pageable.getOffset());
    int end = Math.min(start + pageable.getPageSize(), users.size());

    List<UserAdministrationViewModel> pagedUsers = users.subList(start, end);

    return new PageImpl<>(pagedUsers, pageable, users.size());
  }

}
