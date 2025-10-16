package com.example.mobilele.model.view.user;

public class UserBasicViewModel {
  private String email;
  private String phoneNumber;
  private String firstName;
  private String lastName;

  public UserBasicViewModel() {
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

  public UserBasicViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBasicViewModel setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserBasicViewModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserBasicViewModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }
}
