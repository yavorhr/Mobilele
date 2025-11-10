package com.example.mobilele.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntity {
  private String comment;
  private UserEntity user;
  private Integer rating;
  private Instant created;

  public Feedback() {
  }

  @Column
  public Instant getCreated() {
    return created;
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

  public Feedback setRating(Integer rating) {
    this.rating = rating;
    return this;
  }

  public Feedback setCreated(Instant created) {
    this.created = created;
    return this;
  }

  @PrePersist
  private void preCreate() {
    setCreated(Instant.now());
  }
}
