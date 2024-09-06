package com.example.mobilele.model.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  private String username;
  private String firstName;
  private String lastName;
  private boolean isActive;
  private UserRole role;
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

  @OneToOne
  public UserRole getRole() {
    return role;
  }

  @Column(name = "image_url")
  public String getImageUrl() {
    return imageUrl;
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

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

}
