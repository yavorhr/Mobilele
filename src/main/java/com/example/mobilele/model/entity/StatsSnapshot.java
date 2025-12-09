package com.example.mobilele.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class StatsSnapshot extends BaseEntity {
  private LocalDateTime timestamp;
  private long totalRequests;
  private long anonRequests;
  private long authRequests;
  private String endpointStatsJson;
  private String userStatsJson;

  public StatsSnapshot() {
  }

  @Column
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  @Column
  public long getTotalRequests() {
    return totalRequests;
  }

  @Column
  public long getAnonRequests() {
    return anonRequests;
  }

  @Column
  public long getAuthRequests() {
    return authRequests;
  }

  @Lob
  @Column(columnDefinition = "TEXT")
  public String getEndpointStatsJson() {
    return endpointStatsJson;
  }

  @Lob
  @Column(columnDefinition = "TEXT")
  public String getUserStatsJson() {
    return userStatsJson;
  }

  public StatsSnapshot setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public StatsSnapshot setTotalRequests(long totalRequests) {
    this.totalRequests = totalRequests;
    return this;
  }

  public StatsSnapshot setAnonRequests(long anonRequests) {
    this.anonRequests = anonRequests;
    return this;
  }

  public StatsSnapshot setAuthRequests(long authRequests) {
    this.authRequests = authRequests;
    return this;
  }

  public StatsSnapshot setEndpointStatsJson(String endpointStatsJson) {
    this.endpointStatsJson = endpointStatsJson;
    return this;
  }

  public StatsSnapshot setUserStatsJson(String userStatsJson) {
    this.userStatsJson = userStatsJson;
    return this;
  }
}
