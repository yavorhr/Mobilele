package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.StatsSnapshot;
import com.example.mobilele.model.inMemory.EndpointStats;
import com.example.mobilele.model.inMemory.UserStats;
import com.example.mobilele.model.view.admin.EndpointStatsViewModel;
import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.model.view.admin.UserStatsViewModel;
import com.example.mobilele.repository.StatsRepository;
import com.example.mobilele.service.StatsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StatsServiceImpl implements StatsService {
  private final AtomicLong anonymousRequests = new AtomicLong(0);
  private final AtomicLong authRequests = new AtomicLong(0);
  private final ConcurrentHashMap<String, EndpointStats> endpointStats = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, UserStats> userStats = new ConcurrentHashMap<>();
  private final StatsRepository statsRepository;
  private final ObjectMapper objectMapper;

  public StatsServiceImpl(ObjectMapper objectMapper, StatsRepository statsRepository) {
    this.objectMapper = objectMapper;
    this.statsRepository = statsRepository;
  }

  @Override
  public void onRequest(String path, String username, boolean authenticated, int status, long durationMs) {

    if (authenticated) {
      authRequests.incrementAndGet();
    } else {
      anonymousRequests.incrementAndGet();
    }

    EndpointStats endpoint = endpointStats.computeIfAbsent(path, EndpointStats::new);
    endpoint.record(durationMs, status);

    // per-user stats (only for authenticated users)
    if (authenticated) {
      UserStats uStats = userStats.computeIfAbsent(username, UserStats::new);
      uStats.increment();
    }
  }

  // Show Live stats
  @Override
  public StatsViewModel getStats() {
    long auth = authRequests.get();
    long anon = anonymousRequests.get();
    long total = auth + anon;

    List<EndpointStatsViewModel> endpointViews = endpointStats.values()
            .stream()
            .sorted((a, b) -> Long.compare(b.getTotalRequests(), a.getTotalRequests()))
            .limit(50)
            .map(e -> new EndpointStatsViewModel(
                    e.getPath(),
                    e.getTotalRequests(),
                    e.getAverageDurationMs(),
                    e.getError4xx(),
                    e.getError5xx()
            ))
            .toList();

    List<UserStatsViewModel> userViews = userStats.values()
            .stream()
            .sorted((a, b) -> Long.compare(b.getRequests(), a.getRequests()))
            .map(u -> new UserStatsViewModel(
                    u.getUsername(),
                    u.getRequests()
            ))
            .toList();

    return new StatsViewModel(total, anon, auth, endpointViews, userViews);
  }

  @Override
  public void resetStats() {
    anonymousRequests.set(0);
    authRequests.set(0);

    endpointStats.clear();
    userStats.clear();
  }

  @Override
  public void saveSnapshot(StatsViewModel stats) {
    try {
      StatsSnapshot snapshot = new StatsSnapshot();

      snapshot.setTimestamp(LocalDateTime.now());
      snapshot.setTotalRequests(stats.getTotalRequests());
      snapshot.setAnonRequests(stats.getAnonRequests());
      snapshot.setAuthRequests(stats.getAuthRequests());

      String endpointJson = objectMapper.writeValueAsString(stats.getEndpointStats());
      String userJson = objectMapper.writeValueAsString(stats.getUserStats());

      snapshot.setEndpointStatsJson(endpointJson);
      snapshot.setUserStatsJson(userJson);

      statsRepository.save(snapshot);
    } catch (JsonProcessingException e) {
      // in a real app you might log and/or rethrow as runtime
      throw new RuntimeException("Failed to serialize stats to JSON", e);
    }
  }

  @Override
  public List<StatsViewModel> getAllSnapshots() {
    return statsRepository
            .findAll()
            .stream()
            .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
            .map(this::convertSnapshotToView)
            .toList();
  }

  private StatsViewModel convertSnapshotToView(StatsSnapshot snapshot) {
    try {
      List<EndpointStatsViewModel> endpointList =
              objectMapper.readValue(
                      snapshot.getEndpointStatsJson(),
                      new TypeReference<List<EndpointStatsViewModel>>() {
                      }
              );

      List<UserStatsViewModel> userList =
              objectMapper.readValue(
                      snapshot.getUserStatsJson(),
                      new TypeReference<List<UserStatsViewModel>>() {
                      }
              );

      var vwm = new StatsViewModel(
              snapshot.getTotalRequests(),
              snapshot.getAnonRequests(),
              snapshot.getAuthRequests(),
              endpointList,
              userList);

      vwm.setId(snapshot.getId());
      vwm.setTimeStamp(snapshot.getTimestamp());

      return vwm;

    } catch (Exception e) {
      throw new RuntimeException("Cannot convert snapshot #" + snapshot.getId() + " to StatsViewModel", e);
    }
  }
}


