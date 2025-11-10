package com.example.mobilele.service;

import com.example.mobilele.model.view.feedback.FeedbackSummaryViewModel;
import com.example.mobilele.model.view.feedback.FeedbackViewModel;

import java.util.List;

public interface FeedbackService {
  void leaveFeedback(String username, int rating, String comment);

  List<FeedbackViewModel> findRecentFeedbacks(int i);

  void initFeedbacks();

  FeedbackSummaryViewModel getFeedbackSummary();
}
