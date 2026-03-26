package com.example.mobilele.service;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.repository.StatsRepository;
import com.example.mobilele.service.impl.StatsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class StatsServiceImplTest {

  @Mock
  private StatsRepository statsRepository;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private StatsServiceImpl statsService;

  @Test
  void testOnRequest_authenticatedUser() {
    statsService.onRequest("/api/test", "yavor", true, 200, 100);

    StatsViewModel stats = statsService.getStats();

    assertEquals(1, stats.getTotalRequests());
    assertEquals(1, stats.getAuthRequests());
    assertEquals(0, stats.getAnonRequests());

    assertEquals(1, stats.getUserStats().size());
    assertEquals("yavor", stats.getUserStats().get(0).getUsername());

    assertEquals(1, stats.getEndpointStats().size());
    assertEquals("/api/test", stats.getEndpointStats().get(0).getPath());
  }

  @Test
  void testOnRequest_anonymousUser() {
    statsService.onRequest("/home", null, false, 200, 50);

    StatsViewModel stats = statsService.getStats();

    assertEquals(1, stats.getTotalRequests());
    assertEquals(0, stats.getAuthRequests());
    assertEquals(1, stats.getAnonRequests());

    assertTrue(stats.getUserStats().isEmpty());
  }
}
