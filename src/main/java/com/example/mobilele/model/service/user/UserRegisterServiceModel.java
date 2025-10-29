package com.example.mobilele.model.service.user;

public class UserRegisterServiceModel {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String password;

  public UserRegisterServiceModel() {
  }

  public String getEmail() {
    return email;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRegisterServiceModel setEmail(String email) {
    this.email = email;
    return this;
  }
}
