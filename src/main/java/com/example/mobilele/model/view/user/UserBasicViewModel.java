package com.example.mobilele.model.view.user;

public class UserBasicViewModel {
  private String sellerEmail;
  private String sellerPhoneNumber;
  private String sellerFirstName;
  private String sellerLastName;

  public UserBasicViewModel() {
  }

  public String getFullName() {
    return this.sellerFirstName + " " + this.sellerLastName;
  }

  public String getSellerEmail() {
    return sellerEmail;
  }

  public String getSellerPhoneNumber() {
    return sellerPhoneNumber;
  }

  public String getSellerFirstName() {
    return sellerFirstName;
  }

  public String getSellerLastName() {
    return sellerLastName;
  }

  public UserBasicViewModel setSellerEmail(String sellerEmail) {
    this.sellerEmail = sellerEmail;
    return this;
  }

  public UserBasicViewModel setSellerPhoneNumber(String sellerPhoneNumber) {
    this.sellerPhoneNumber = sellerPhoneNumber;
    return this;
  }

  public UserBasicViewModel setSellerFirstName(String sellerFirstName) {
    this.sellerFirstName = sellerFirstName;
    return this;
  }

  public UserBasicViewModel setSellerLastName(String sellerLastName) {
    this.sellerLastName = sellerLastName;
    return this;
  }
}
