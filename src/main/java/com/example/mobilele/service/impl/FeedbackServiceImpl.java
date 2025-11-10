package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Feedback;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.FeedbackViewModel;
import com.example.mobilele.repository.FeedbackRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
  public void initFeedbacks() {
    if (feedbackRepository.count() == 0) {


      UserEntity admin = this.userRepository
              .findByUsername("admin")
              .orElseThrow(() -> new ObjectNotFoundException("User with username: admin does not exist!"));

      UserEntity user = this.userRepository
              .findByUsername("user")
              .orElseThrow(() -> new ObjectNotFoundException("User with username: admin does not exist!"));

      Feedback feedback = createFeedback(admin, "The app is very well made!", 5, Instant.now());
      Feedback feedback2 = createFeedback(user, "I just don't like it!", 1, Instant.now());
      Feedback feedback3 = createFeedback(user, "The application is cool, but there are no many offers yet!", 4, Instant.now());
      Feedback feedback4 = createFeedback(admin, "You rock!", 5, Instant.now());
      Feedback feedback5 = createFeedback(admin, "Just found my future car here!", 5, Instant.now());

      feedbackRepository.saveAll(List.of(feedback, feedback2, feedback3, feedback4, feedback5));
    }
  }

  private Feedback createFeedback(UserEntity userEntity, String comment, int rating, Instant created) {
    Feedback feedback = new Feedback();
    feedback.setUser(userEntity);
    feedback.setComment(comment);
    feedback.setRating(rating);
    feedback.setCreated(created);

    return feedback;
  }
}
