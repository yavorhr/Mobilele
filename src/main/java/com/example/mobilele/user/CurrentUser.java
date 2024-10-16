package com.example.mobilele.user;

import com.example.mobilele.model.entity.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class CurrentUser {
  private Long id;
  private boolean isLoggedIn;
  private String username;
  private String firstName;
  private String lastName;
  private Set<UserRoleEnum> userRoles;

  public CurrentUser() {
    this.userRoles = new HashSet<>();
  }

  public Long getId() {
    return id;
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  public String getUsername() {
    return username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Set<UserRoleEnum> getUserRoles() {
    return userRoles;
  }

  public void setLoggedIn(boolean loggedIn) {
    isLoggedIn = loggedIn;
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

  public void setUserRoles(Set<UserRoleEnum> userRoles) {
    this.userRoles = userRoles;
  }

  public void addRole(UserRoleEnum role) {
    this.userRoles.add(role);
  }

  public boolean isAdmin() {
    return this.userRoles.contains(UserRoleEnum.ADMIN);
  }

  public CurrentUser setId(Long id) {
    this.id = id;
    return this;
  }

  public void clean() {
    this.setFirstName("");
    this.setLastName("");
    this.setLoggedIn(false);
    this.setUsername("");
    this.clearRoles();
    this.setId(0L);
  }

  private void clearRoles() {
    this.userRoles.clear();
  }
}
