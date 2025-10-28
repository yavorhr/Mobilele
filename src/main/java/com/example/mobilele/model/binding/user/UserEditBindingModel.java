package com.example.mobilele.model.binding.user;

public class UserEditBindingModel {
  private String phoneNumber;
  private String firstName;
  private String lastName;

  public UserEditBindingModel() {
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

  public UserEditBindingModel setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public UserEditBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserEditBindingModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }
}
