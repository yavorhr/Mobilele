package com.example.mobilele.model.view;

public class UserStatsViewModel {
  private final String username;
  private final long requests;

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
}
