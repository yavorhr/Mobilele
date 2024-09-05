package com.example.mobilele.model.entity;

import com.example.mobilele.model.entity.enums.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
  private RoleEnum name;

  public UserRole() {
  }

  @Enumerated
  public RoleEnum getName() {
    return name;
  }

  public void setName(RoleEnum name) {
    this.name = name;
  }
}
