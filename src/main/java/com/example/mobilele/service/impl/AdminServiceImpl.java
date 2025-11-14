package com.example.mobilele.service.impl;

import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

  @Override
  public Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String query, PageRequest of) {
    return null;
  }

}
