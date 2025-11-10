package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Feedback;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.FeedbackRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
  private final FeedbackRepository feedbackRepository;
  private final UserRepository userRepository;

  public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository) {
    this.feedbackRepository = feedbackRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void leaveFeedback(String username, int rating, String comment) {

    UserEntity user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User with username : " + username + " was not found"));

    Feedback feedback = new Feedback();
    feedback.setUser(user);
    feedback.setRating(rating);
    feedback.setComment(comment);

    feedbackRepository.save(feedback);
  }
}
