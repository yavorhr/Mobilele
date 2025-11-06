package com.example.mobilele.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
  private List<UserRoleEntity> roles;
  private String username;
  private String email;
  private String password;
  private String phoneNumber;
  private String firstName;
  private String lastName;
  private Set<OfferEntity> favorites;
  // Account lock properties
  private Integer failedLoginAttempts;
  private boolean accountLocked;
  private LocalDateTime lockTime;
  private Set<OfferEntity> offers;

  public UserEntity() {
    this.favorites = new HashSet<>();
    this.accountLocked = false;
    this.failedLoginAttempts = 0;
  }

  @OneToMany(mappedBy = "seller")
  public Set<OfferEntity> getOffers() {
    return offers;
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "users_favorites",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "offer_id"))
  public Set<OfferEntity> getFavorites() {
    return favorites;
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

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "roles_id")
  )
  public List<UserRoleEntity> getRoles() {
    return roles;
  }

  @Column(nullable = false)
  public String getEmail() {
    return email;
  }

  @Column(nullable = false, name = "phone_number")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Integer getFailedLoginAttempts() {
    return failedLoginAttempts;
  }

  public boolean isAccountLocked() {
    return accountLocked;
  }

  public LocalDateTime getLockTime() {
    return lockTime;
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

  public UserEntity setFavorites(Set<OfferEntity> favorites) {
    this.favorites = favorites;
    return this;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserEntity setOffers(Set<OfferEntity> offers) {
    this.offers = offers;
    return this;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserEntity setRoles(List<UserRoleEntity> roles) {
    this.roles = roles;
    return this;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserEntity setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserEntity setFailedLoginAttempts(Integer failedLoginAttempts) {
    this.failedLoginAttempts = failedLoginAttempts;
    return this;
  }

  public UserEntity setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
    return this;
  }

  public UserEntity setLockTime(LocalDateTime lockTime) {
    this.lockTime = lockTime;
    return this;
  }
}
