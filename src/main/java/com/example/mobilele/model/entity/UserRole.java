package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
  private UserRoleEnum role;

  public UserRole() {
  }

  @Enumerated
  @Column(nullable = false)
  public UserRoleEnum getRole() {
    return role;
  }

  public void setRole(UserRoleEnum name) {
    this.role = name;
  }
}
