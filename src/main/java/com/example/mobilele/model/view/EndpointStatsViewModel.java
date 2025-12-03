package com.example.mobilele.model.view;

public class EndpointStatsViewModel {
  private final String path;
  private final long totalRequests;
  private final double avgResponseTimeMs;
  private final long error4xx;
  private final long error5xx;

  public EndpointStatsViewModel(String path,
                                long totalRequests,
                                double avgResponseTimeMs,
                                long error4xx,
                                long error5xx) {
    this.path = path;
    this.totalRequests = totalRequests;
    this.avgResponseTimeMs = avgResponseTimeMs;
    this.error4xx = error4xx;
    this.error5xx = error5xx;
  }

  public String getPath() {
    return path;
  }

  public long getTotalRequests() {
    return totalRequests;
  }

  public double getAvgResponseTimeMs() {
    return avgResponseTimeMs;
  }

  public long getError4xx() {
    return error4xx;
  }

  public long getError5xx() {
    return error5xx;
  }
}
