package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Feedback;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.FeedbackRepository;
import com.example.mobilele.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}
