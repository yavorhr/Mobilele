package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.StatsSnapshot;
import com.example.mobilele.model.view.admin.EndpointStatsViewModel;
import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.model.view.admin.UserStatsViewModel;
import com.example.mobilele.repository.StatsRepository;
import com.example.mobilele.service.impl.StatsServiceImpl;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

  @Mock
  private StatsRepository statsRepository;

  private StatsServiceImpl statsService;

  @BeforeEach
  void setUp() {
    statsService = new StatsServiceImpl(new ObjectMapper(), statsRepository);
  }

  @AfterEach
  void tearDown() {
    statsService.resetStats();
  }

  @Test
  void onRequest_authenticatedUser_updatesStatsCorrectly() {
    statsService.onRequest("/api/test", "yavor", true, 200, 120);

    StatsViewModel stats = statsService.getStats();

    assertEquals(1, stats.getTotalRequests());
    assertEquals(1, stats.getAuthRequests());
    assertEquals(0, stats.getAnonRequests());

    assertEquals(1, stats.getEndpointStats().size());
    assertEquals("/api/test", stats.getEndpointStats().get(0).getPath());

    assertEquals(1, stats.getUserStats().size());
    assertEquals("yavor", stats.getUserStats().get(0).getUsername());
  }

  @Test
  void onRequest_anonymousUser_doesNotCreateUserStats() {
    statsService.onRequest("/home", null, false, 200, 50);

    StatsViewModel stats = statsService.getStats();

    assertEquals(1, stats.getTotalRequests());
    assertEquals(1, stats.getAnonRequests());
    assertTrue(stats.getUserStats().isEmpty());
  }

  @Test
  void getStats_sortsEndpointsAndUsersCorrectly() {
    statsService.onRequest("/a", "user1", true, 200, 100);
    statsService.onRequest("/a", "user1", true, 200, 200);
    statsService.onRequest("/b", "user2", true, 500, 300);

    StatsViewModel stats = statsService.getStats();

    // endpoint with most requests first
    assertEquals("/a", stats.getEndpointStats().get(0).getPath());
    assertEquals(2, stats.getEndpointStats().get(0).getTotalRequests());

    // user with most requests first
    assertEquals("user1", stats.getUserStats().get(0).getUsername());
    assertEquals(2, stats.getUserStats().get(0).getRequests());
  }

  @Test
  void resetStats_clearsAllData() {
    statsService.onRequest("/a", "user", true, 200, 100);

    statsService.resetStats();

    StatsViewModel stats = statsService.getStats();

    assertEquals(0, stats.getTotalRequests());
    assertTrue(stats.getEndpointStats().isEmpty());
    assertTrue(stats.getUserStats().isEmpty());
  }

  @Test
  void saveSnapshot_persistsCorrectData() {
    StatsViewModel stats = new StatsViewModel(
            5,
            2,
            3,
            List.of(new EndpointStatsViewModel("/a", 5, 100, 0, 0)),
            List.of(new UserStatsViewModel("user", 5))
    );

    statsService.saveSnapshot(stats);

    verify(statsRepository).save(argThat(snapshot ->
            snapshot.getTotalRequests() == 5 &&
                    snapshot.getAnonRequests() == 2 &&
                    snapshot.getAuthRequests() == 3 &&
                    snapshot.getEndpointStatsJson() != null &&
                    snapshot.getUserStatsJson() != null
    ));
  }

  @Test
  void getAllSnapshots_returnsMappedViewModels() {
    StatsSnapshot snapshot = new StatsSnapshot();
    snapshot.setId(1L);
    snapshot.setTimestamp(LocalDateTime.now());
    snapshot.setTotalRequests(10);
    snapshot.setAnonRequests(4);
    snapshot.setAuthRequests(6);
    snapshot.setEndpointStatsJson("[]");
    snapshot.setUserStatsJson("[]");

    when(statsRepository.findAll()).thenReturn(List.of(snapshot));

    List<StatsViewModel> result = statsService.getAllSnapshots();

    assertEquals(1, result.size());
    assertEquals(10, result.get(0).getTotalRequests());
    assertEquals(1L, result.get(0).getId());
  }

  @Test
  void getSnapshotViewById_returnsCorrectViewModel() {
    StatsSnapshot snapshot = new StatsSnapshot();
    snapshot.setId(1L);
    snapshot.setTimestamp(LocalDateTime.now());
    snapshot.setTotalRequests(8);
    snapshot.setAnonRequests(3);
    snapshot.setAuthRequests(5);
    snapshot.setEndpointStatsJson("[]");
    snapshot.setUserStatsJson("[]");

    when(statsRepository.findById(1L)).thenReturn(Optional.of(snapshot));

    StatsViewModel result = statsService.getSnapshotViewById(1L);

    assertEquals(8, result.getTotalRequests());
    assertEquals(1L, result.getId());
  }

  @Test
  void getSnapshotViewById_throwsWhenNotFound() {
    when(statsRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> statsService.getSnapshotViewById(1L));
  }
}

