package com.example.mobilele.model.view.admin;

public class UserStatsViewModel {
  private String username;
  private long requests;

  public UserStatsViewModel(String username, long requests) {
    this.username = username;
    this.requests = requests;
  }

  public String getUsername() {
    return username;
  }

  public long getRequests() {
    return requests;
  }

  public UserStatsViewModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserStatsViewModel setRequests(long requests) {
    this.requests = requests;
    return this;
  }
}
