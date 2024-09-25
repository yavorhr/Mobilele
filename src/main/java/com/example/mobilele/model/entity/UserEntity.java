package com.example.mobilele.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private boolean isActive;
  private List<UserRole> roles;
  private String imageUrl;

  public UserEntity() {
  }

  @Column(unique = true, nullable = false)
  public String getUsername() {
    return username;
  }

  @Column(name = "first_name")
  public String getFirstName() {
    return firstName;
  }

  @Column(name = "last_name")
  public String getLastName() {
    return lastName;
  }

  @Column(name = "is_active")
  public boolean isActive() {
    return isActive;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  public List<UserRole> getRoles() {
    return roles;
  }

  @Column(name = "image_url")
  public String getImageUrl() {
    return imageUrl;
  }

  @Column(name = "password", nullable = false)
  public String getPassword() {
    return password;
  }

  public UserEntity setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserEntity setActive(boolean active) {
    isActive = active;
    return this;
  }

  public UserEntity setRoles(List<UserRole> roles) {
    this.roles = roles;
    return this;
  }

  public UserEntity setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }
}
