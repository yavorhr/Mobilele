package com.example.mobilele.model.view.admin;

import java.util.List;

public class StatsViewModel {
  private final long totalRequests;
  private final long anonRequests;
  private final long authRequests;
  private final List<EndpointStatsViewModel> endpointStats;
  private final List<UserStatsViewModel> userStats;

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
}
