package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Feedback;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.feedback.FeedbackSummaryViewModel;
import com.example.mobilele.model.view.feedback.FeedbackViewModel;
import com.example.mobilele.repository.FeedbackRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
  @Mock
  private FeedbackRepository feedbackRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private FeedbackServiceImpl feedbackService;

  private UserEntity user;


  @BeforeEach
  void init() {
    user = new UserEntity();
    user.setUsername("user");
    user.setId(1L);

  }

  @Test
  void testLeaveFeedback_Success() {
    when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

    feedbackService.leaveFeedback("user", 5, "Great app!");

    verify(feedbackRepository).save(argThat(feedback ->
            feedback.getUser().equals(user) &&
                    feedback.getRating() == 5 &&
                    feedback.getComment().equals("Great app!")));
  }

  @Test
  void testLeaveFeedback_UserNotFound() {
    when(userRepository.findByUsername("missing"))
            .thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> feedbackService.leaveFeedback("missing", 5, "test"));
  }

  @Test
  void testFindRecentFeedbacks() {
    Feedback feedback = new Feedback();
    FeedbackViewModel viewModel = new FeedbackViewModel();

    when(feedbackRepository.findAllByOrderByCreatedDesc(PageRequest.of(0, 2)))
            .thenReturn(List.of(feedback));

    when(modelMapper.map(feedback, FeedbackViewModel.class))
            .thenReturn(viewModel);

    List<FeedbackViewModel> result = feedbackService.findRecentFeedbacks(2);

    assertEquals(1, result.size());
    assertEquals(viewModel, result.get(0));
  }

  @Test
  void testGetFeedbackSummary_WithData() {
    when(feedbackRepository.findAverageRating())
            .thenReturn(Optional.of(4.5));
    when(feedbackRepository.count())
            .thenReturn(10L);

    FeedbackSummaryViewModel result = feedbackService.getFeedbackSummary();

    assertEquals(4.5, result.getRating());
    assertEquals(10L, result.getCount());
  }
}
