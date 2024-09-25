package com.example.mobilele.model.dto.binding;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterBindingModel {
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String confirmPassword;

  public UserRegisterBindingModel() {
  }

  @NotNull
  @Size(min = 2,max = 20)
  public String getUsername() {
    return username;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  @NotNull
  @Size(min = 4,max = 20)
  public String getFirstName() {
    return firstName;
  }

  @NotNull
  @Size(min = 4,max = 20)
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

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}
