package com.example.mobilele.model.view.admin;

import java.time.LocalDateTime;
import java.util.List;

public class StatsViewModel {
  private Long id;
  private long totalRequests;
  private long anonRequests;
  private long authRequests;
  private LocalDateTime timeStamp;
  private List<EndpointStatsViewModel> endpointStats;
  private List<UserStatsViewModel> userStats;

  public StatsViewModel(long totalRequests,
                        long anonRequests,
                        long authRequests,
                        List<EndpointStatsViewModel> endpointStats,
                        List<UserStatsViewModel> userStats) {
    this.totalRequests = totalRequests;
    this.anonRequests = anonRequests;
    this.authRequests = authRequests;

    this.endpointStats = endpointStats;
    this.userStats = userStats;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public long getTotalRequests() {
    return totalRequests;
  }

  public long getAnonRequests() {
    return anonRequests;
  }

  public long getAuthRequests() {
    return authRequests;
  }

  public List<EndpointStatsViewModel> getEndpointStats() {
    return endpointStats;
  }

  public List<UserStatsViewModel> getUserStats() {
    return userStats;
  }

  public StatsViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public StatsViewModel setTotalRequests(long totalRequests) {
    this.totalRequests = totalRequests;
    return this;
  }

  public StatsViewModel setTimeStamp(LocalDateTime timeStamp) {
    this.timeStamp = timeStamp;
    return this;
  }
}
