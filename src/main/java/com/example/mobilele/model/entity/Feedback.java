package com.example.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "brands")
public class Feedback extends BaseEntity {
  private String review;
  private UserEntity user;
  private int rating;

  public Feedback() {

  }

  @Column(nullable = false, length = 500)
  public String getReview() {
    return review;
  }

  @ManyToOne(optional = false)
  public UserEntity getUser() {
    return user;
  }

  @Column(nullable = false)
  public int getRating() {
    return rating;
  }

  public Feedback setReview(String review) {
    this.review = review;
    return this;
  }

  public Feedback setUser(UserEntity user) {
    this.user = user;
    return this;
  }

  public Feedback setRating(int rating) {
    this.rating = rating;
    return this;
  }
}
