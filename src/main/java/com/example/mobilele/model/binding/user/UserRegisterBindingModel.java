package com.example.mobilele.model.binding.user;
import com.example.mobilele.validator.DoesPasswordAndConfirmPasswordMatch;
import com.example.mobilele.validator.UniqueEmail;
import com.example.mobilele.validator.UniquePhoneNumber;
import com.example.mobilele.validator.UniqueUsername;
import jakarta.validation.constraints.*;

@DoesPasswordAndConfirmPasswordMatch
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

  @UniqueUsername
  @Size(min = 2,max = 20, message = "Must be between 2 and 20 symbols")
  public String getUsername() {
    return username;
  }

  @UniqueEmail
  @Pattern(
          regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
          message = "Invalid email format")
  public String getEmail() {
    return email;
  }

  @Pattern(
          regexp = "^\\+359\\d{9}$",
          message = "Must start with +359 and contains 9 digits after that")
  @UniquePhoneNumber
  public String getPhoneNumber() {
    return phoneNumber;
  }

  @NotNull
  @NotBlank
  public String getConfirmPassword() {
    return confirmPassword;
  }

  @Size(min = 2,max = 20, message = "Must be between 4 and 20 symbols")
  public String getFirstName() {
    return firstName;
  }

  @Size(min = 2,max = 20, message = "Must be between 4 and 20 symbols")
  public String getLastName() {
    return lastName;
  }

  @Size(min = 5)
  @NotBlank
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
