package com.example.mobilele.repository;

import com.example.mobilele.model.entity.UserRole;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

  UserRole findByRole(UserRoleEnum userRoleEnum);
}
