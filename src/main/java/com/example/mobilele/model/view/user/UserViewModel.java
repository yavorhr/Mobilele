package com.example.mobilele.model.view.user;

public class UserViewModel {
  private String email;
  private String phoneNumber;
  private String username;
  private String firstName;
  private String lastName;

  public UserViewModel() {
  }

  public String getUsername() {
    return username;
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public UserViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserViewModel setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserViewModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserViewModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserViewModel setUsername(String username) {
    this.username = username;
    return this;
  }
}
