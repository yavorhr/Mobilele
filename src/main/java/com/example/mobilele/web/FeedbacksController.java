package com.example.mobilele.web;

import com.example.mobilele.service.FeedbackService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class FeedbacksController {
  private final FeedbackService feedbackService;

  public FeedbacksController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/submit-feedback")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> submitFeedback(
          @RequestParam int rating,
          @RequestParam String comment,
          @AuthenticationPrincipal MobileleUser principal) {

    Map<String, Object> response = new HashMap<>();

    if (rating < 1) {
      response.put("success", false);
      response.put("message", "Please select at least one star.");
      return ResponseEntity.badRequest().body(response);
    }

    if (comment == null || comment.trim().length() < 5) {
      response.put("success", false);
      response.put("message", "Comment must be at least 5 characters long.");
      return ResponseEntity.badRequest().body(response);
    }

    try {
      feedbackService.leaveFeedback(principal.getUsername(), rating, comment);
      response.put("success", true);
      response.put("message", "Thank you for your feedback!");
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.put("success", false);
      response.put("message", e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }
}
