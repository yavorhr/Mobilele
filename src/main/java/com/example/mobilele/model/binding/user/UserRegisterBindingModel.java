package com.example.mobilele.model.binding.user;

import com.example.mobilele.validator.DoesPasswordAndConfirmPasswordMatch;
import com.example.mobilele.validator.UniqueEmail;
import com.example.mobilele.validator.UniquePhoneNumber;
import com.example.mobilele.validator.UniqueUsername;
import jakarta.validation.constraints.*;

@DoesPasswordAndConfirmPasswordMatch(message = "{validation.password.match}")
public class UserRegisterBindingModel {

  private String username;
  private String firstName;
  private String phoneNumber;
  private String lastName;
  private String email;
  private String password;
  private String confirmPassword;

  public UserRegisterBindingModel() {
  }

  @UniqueUsername(message = "{validation.username.unique}")
  @NotBlank(message = "{validation.required}")
  @Size(min = 2, max = 20, message = "{validation.size}")
  public String getUsername() {
    return username;
  }

  @UniqueEmail(message = "{validation.email.unique}")
  @NotBlank(message = "{validation.required}")
  @Email(message = "{validation.email.invalid}")
  public String getEmail() {
    return email;
  }

  @UniquePhoneNumber(message = "{validation.phone.unique}")
  @Pattern(
          regexp = "^\\+359\\d{9}$",
          message = "{validation.phone.invalid}"
  )
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @NotBlank(message = "{validation.required}")
  @Size(min = 5, max = 20, message = "{validation.size}")
  public String getPassword() {
    return password;
  }

  @NotBlank(message = "{validation.required}")
  @Size(min = 5, max = 20, message = "{validation.size}")
  public String getConfirmPassword() {
    return confirmPassword;
  }

  @NotBlank(message = "{validation.required}")
  @Size(min = 2, max = 20, message = "{validation.size}")
  public String getFirstName() {
    return firstName;
  }

  @NotBlank(message = "{validation.required}")
  @Size(min = 2, max = 20, message = "{validation.size}")
  public String getLastName() {
    return lastName;
  }

  // setters

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

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public UserRegisterBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserRegisterBindingModel setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }
}
