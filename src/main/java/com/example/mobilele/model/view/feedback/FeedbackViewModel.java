package com.example.mobilele.model.view.feedback;

import java.time.Instant;

public class FeedbackViewModel {
  private int rating;
  private String userFirstName;
  private String userLastName;
  private String comment;
  private Instant created;

  public FeedbackViewModel() {
  }

  public Instant getCreated() {
    return created;
  }

  public int getRating() {
    return rating;
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public String getComment() {
    return comment;
  }

  public FeedbackViewModel setRating(int rating) {
    this.rating = rating;
    return this;
  }

  public FeedbackViewModel setUserFirstName(String userFirstName) {
    this.userFirstName = userFirstName;
    return this;
  }

  public FeedbackViewModel setUserLastName(String userLastName) {
    this.userLastName = userLastName;
    return this;
  }

  public FeedbackViewModel setComment(String comment) {
    this.comment = comment;
    return this;
  }

  public FeedbackViewModel setCreated(Instant created) {
    this.created = created;
    return this;
  }
}
