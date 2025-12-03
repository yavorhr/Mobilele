package com.example.mobilele.model.entity;

import java.util.concurrent.atomic.AtomicLong;

public class UserStats {
  private final String username;
  private final AtomicLong requests = new AtomicLong(0);

  public UserStats(String username) {
    this.username = username;
  }

  public void increment() {
    requests.incrementAndGet();
  }

  public String getUsername() {
    return username;
  }

  public long getRequests() {
    return requests.get();
  }
}
