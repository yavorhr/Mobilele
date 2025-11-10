package com.example.mobilele.model.view.feedback;

public class FeedbackSummaryViewModel {
  private double rating;
  private long count;

  public FeedbackSummaryViewModel() {
  }

  public FeedbackSummaryViewModel(double rating, long count) {
    this.rating = rating;
    this.count = count;
  }

  public double getRating() {
    return rating;
  }

  public long getCount() {
    return count;
  }

  public FeedbackSummaryViewModel setRating(double rating) {
    this.rating = rating;
    return this;
  }

  public FeedbackSummaryViewModel setCount(long count) {
    this.count = count;
    return this;
  }
}
