package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
  private UserRoleEnum role;

  public UserRoleEntity() {
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
