package com.example.mobilele.model.view.user;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import java.time.LocalDateTime;
import java.util.Set;

public class UserAdministrationViewModel {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime registrationDate;
  private boolean accountLocked;
  private Integer timesLocked;
  private Set<UserRoleEnum> roles;
  private boolean isEnabled;

  public UserAdministrationViewModel() {
  }

  public Set<UserRoleEnum> getRoles() {
    return roles;
  }

  public boolean isAccountLocked() {
    return accountLocked;
  }

  public Integer getTimesLocked() {
    return timesLocked;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public UserAdministrationViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserAdministrationViewModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserAdministrationViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserAdministrationViewModel setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }

  public UserAdministrationViewModel setEnabled(boolean enabled) {
    isEnabled = enabled;
    return this;
  }

  public UserAdministrationViewModel setRoles(Set<UserRoleEnum> roles) {
    this.roles = roles;
    return this;
  }

  public UserAdministrationViewModel setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
    return this;
  }

  public UserAdministrationViewModel setTimesLocked(Integer timesLocked) {
    this.timesLocked = timesLocked;
    return this;
  }
}
