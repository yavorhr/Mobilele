package com.example.mobilele.service;

import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

  Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String query, Pageable pageable);
}
