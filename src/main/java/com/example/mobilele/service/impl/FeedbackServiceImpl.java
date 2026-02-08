package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Feedback;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.feedback.FeedbackSummaryViewModel;
import com.example.mobilele.model.view.feedback.FeedbackViewModel;
import com.example.mobilele.repository.FeedbackRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {
  private final FeedbackRepository feedbackRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserRepository userRepository, ModelMapper modelMapper) {
    this.feedbackRepository = feedbackRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
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

  @Override
  public List<FeedbackViewModel> findRecentFeedbacks(int count) {
    return this.feedbackRepository
            .findAllByOrderByCreatedDesc((PageRequest.of(0, count)))
            .stream()
            .map(o -> modelMapper.map(o, FeedbackViewModel.class))
            .collect(Collectors.toList());
  }

  @Override
  public FeedbackSummaryViewModel getFeedbackSummary() {
    double avgRating = this.feedbackRepository.findAverageRating().orElse(0.0);
    long count = this.feedbackRepository.count();

    return new FeedbackSummaryViewModel(avgRating, count);
  }

  // Init feedbacks
  @Override
  public void initFeedbacks() {
    if (feedbackRepository.count() > 0) {
      return;
    }

    UserEntity admin = getUser("admin");
    UserEntity user = getUser("user");

    List<Feedback> feedbacks = List.of(
            new Feedback(admin, "The app is very well made!", 5),
            new Feedback(user, "I just don't like it!", 1),
            new Feedback(user, "The application is cool, but there are not many offers yet!", 4),
            new Feedback(admin, "You rock!", 5),
            new Feedback(admin, "Just found my future car here!", 5)
    );

    feedbackRepository.saveAll(feedbacks);
  }

  private UserEntity getUser(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with username: " + username + " does not exist!"));
  }
}
