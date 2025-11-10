package com.example.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {
  private String comment;
  private UserEntity user;
  private Integer rating;

  public Feedback() {

  }

  @Column(nullable = false, length = 500)
  public String getComment() {
    return comment;
  }

  @ManyToOne(optional = false)
  public UserEntity getUser() {
    return user;
  }

  @Column(nullable = false)
  public int getRating() {
    return rating;
  }

  public Feedback setComment(String comment) {
    this.comment = comment;
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
