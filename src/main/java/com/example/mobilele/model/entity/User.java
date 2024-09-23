package com.example.mobilele.model.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private boolean isActive;
  private List<UserRole> roles;
  private String imageUrl;

  public User() {
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

  public void setUsername(String username) {
    this.username = username;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(List<UserRole> roles) {
    this.roles = roles;
  }
}
