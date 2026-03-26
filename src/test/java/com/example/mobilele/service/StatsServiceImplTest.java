package com.example.mobilele.service;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.repository.StatsRepository;
import com.example.mobilele.service.impl.StatsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

  @Test
  void testGetStats_multipleRequests_sortedCorrectly() {
    statsService.onRequest("/a", "user1", true, 200, 100);
    statsService.onRequest("/a", "user1", true, 200, 200);
    statsService.onRequest("/b", "user2", true, 500, 300);

    StatsViewModel stats = statsService.getStats();

    // endpoint /a should be first (2 requests)
    assertEquals("/a", stats.getEndpointStats().get(0).getPath());
    assertEquals(2, stats.getEndpointStats().get(0).getTotalRequests());

    // user1 should be first
    assertEquals("user1", stats.getUserStats().get(0).getUsername());
    assertEquals(2, stats.getUserStats().get(0).getRequests());
  }

  @Test
  void testResetStats() {
    statsService.onRequest("/a", "user", true, 200, 100);

    statsService.resetStats();

    StatsViewModel stats = statsService.getStats();

    assertEquals(0, stats.getTotalRequests());
    assertTrue(stats.getEndpointStats().isEmpty());
    assertTrue(stats.getUserStats().isEmpty());
  }

  @Test
  void testSaveSnapshot_jsonError() throws Exception {
    StatsViewModel stats = new StatsViewModel(1, 1, 0, List.of(), List.of());

    when(objectMapper.writeValueAsString(any()))
            .thenThrow(new JsonProcessingException("boom") {});

    assertThrows(RuntimeException.class,
            () -> statsService.saveSnapshot(stats));
  }
}
