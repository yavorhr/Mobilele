package com.example.mobilele.user;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {
  private boolean isLoggedIn;
  private String username;
  private String firstName;
  private String lastName;

  public CurrentUser() {
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

  public void clean() {
    this.setFirstName("");
    this.setLastName("");
    this.setLoggedIn(false);
    this.setUsername("");
  }
}
