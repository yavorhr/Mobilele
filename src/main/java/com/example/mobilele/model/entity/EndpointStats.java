package com.example.mobilele.model.entity;

import java.util.concurrent.atomic.AtomicLong;

public class EndpointStats {
  private final String path;
  private final AtomicLong totalRequests ;
  private final AtomicLong totalDurationMs;
  private final AtomicLong error4xx ;
  private final AtomicLong error5xx;

  public EndpointStats(String path, AtomicLong totalRequests, AtomicLong totalDurationMs, AtomicLong error4xx, AtomicLong error5xx) {
    this.path = path;
    this.totalRequests = totalRequests;
    this.totalDurationMs = totalDurationMs;
    this.error4xx = error4xx;
    this.error5xx = error5xx;
  }

  public EndpointStats(String path) {
    this.path = path;
    this.totalRequests = new AtomicLong(0);
    this.totalDurationMs = new AtomicLong(0);
    this.error4xx = new AtomicLong(0);
    this.error5xx = new AtomicLong(0);
  }

  public void record(long durationMs, int status) {
    totalRequests.incrementAndGet();
    totalDurationMs.addAndGet(durationMs);

    if (status >= 400 && status < 500) {
      error4xx.incrementAndGet();
    } else if (status >= 500 && status < 600) {
      error5xx.incrementAndGet();
    }
  }

  public String getPath() {
    return path;
  }

  public long getTotalRequests() {
    return totalRequests.get();
  }

  public long getError4xx() {
    return error4xx.get();
  }

  public long getError5xx() {
    return error5xx.get();
  }

  public double getAverageDurationMs() {
    long count = totalRequests.get();
    return count == 0 ? 0.0 : (double) totalDurationMs.get() / count;
  }
}
