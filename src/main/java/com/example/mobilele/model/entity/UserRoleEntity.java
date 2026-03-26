package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
  private UserRoleEnum role;

  public UserRoleEntity() {
  }

  public UserRoleEntity(UserRoleEnum role) {
    this.role = role;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)

  public UserRoleEnum getRole() {
    return role;
  }

  public void setRole(UserRoleEnum name) {
    this.role = name;
  }
}
