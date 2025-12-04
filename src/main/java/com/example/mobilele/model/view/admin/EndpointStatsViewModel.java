package com.example.mobilele.model.view.admin;

public class EndpointStatsViewModel {
  private String path;
  private long totalRequests;
  private double avgResponseTimeMs;
  private long error4xx;
  private long error5xx;

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

  public EndpointStatsViewModel() {
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

  public EndpointStatsViewModel setPath(String path) {
    this.path = path;
    return this;
  }

  public EndpointStatsViewModel setTotalRequests(long totalRequests) {
    this.totalRequests = totalRequests;
    return this;
  }

  public EndpointStatsViewModel setAvgResponseTimeMs(double avgResponseTimeMs) {
    this.avgResponseTimeMs = avgResponseTimeMs;
    return this;
  }

  public EndpointStatsViewModel setError4xx(long error4xx) {
    this.error4xx = error4xx;
    return this;
  }

  public EndpointStatsViewModel setError5xx(long error5xx) {
    this.error5xx = error5xx;
    return this;
  }
}
