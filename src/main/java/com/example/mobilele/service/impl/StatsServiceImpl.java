package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.EndpointStats;
import com.example.mobilele.model.entity.enums.UserStats;
import com.example.mobilele.model.view.EndpointStatsViewModel;
import com.example.mobilele.model.view.StatsViewModel;
import com.example.mobilele.model.view.UserStatsViewModel;
import com.example.mobilele.service.StatsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StatsServiceImpl implements StatsService {
  private final AtomicLong anonymousRequests = new AtomicLong(0);
  private final AtomicLong authRequests = new AtomicLong(0);

  private final ConcurrentHashMap<String, EndpointStats> endpointStats = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, UserStats> userStats = new ConcurrentHashMap<>();


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

  @Override
  public StatsViewModel getStats() {
    long auth = authRequests.get();
    long anon = anonymousRequests.get();
    long total = auth + anon;

    // build DTOs for view
    List<EndpointStatsViewModel> endpointViews = endpointStats.values()
            .stream()
            .sorted((a, b) -> Long.compare(b.getTotalRequests(), a.getTotalRequests())) // most viewed first
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

  }
}
