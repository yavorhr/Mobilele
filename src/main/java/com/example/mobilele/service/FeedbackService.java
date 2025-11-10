package com.example.mobilele.service;

import com.example.mobilele.model.view.FeedbackViewModel;

import java.util.List;

public interface FeedbackService {
  void leaveFeedback(String username, int rating, String comment);

  List<FeedbackViewModel> findLatestReviews(int i);

  void initFeedbacks();
}
