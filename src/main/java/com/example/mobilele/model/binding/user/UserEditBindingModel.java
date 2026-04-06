package com.example.mobilele.model.binding.user;

import com.example.mobilele.validator.UniquePhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserEditBindingModel {
  private String phoneNumber;
  private String firstName;
  private String lastName;

  public UserEditBindingModel() {
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
  @Size(min = 2, max = 20, message = "{validation.size}")
  public String getFirstName() {
    return firstName;
  }

  @NotBlank(message = "{validation.required}")
  @Size(min = 2, max = 20, message = "{validation.size}")
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
