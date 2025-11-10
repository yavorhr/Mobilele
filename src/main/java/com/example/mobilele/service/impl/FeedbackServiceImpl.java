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
  public List<FeedbackViewModel> findLatestReviews(int count) {
    return this.feedbackRepository
            .findAllByOrderByCreatedDesc((PageRequest.of(0, count)))
            .stream()
            .map(o -> modelMapper.map(o, FeedbackViewModel.class))
            .collect(Collectors.toList());
  }
}
