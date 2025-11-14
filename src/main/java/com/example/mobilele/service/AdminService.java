package com.example.mobilele.service;

import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AdminService {

  Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String query, PageRequest of);
}
