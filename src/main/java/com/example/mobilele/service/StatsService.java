package com.example.mobilele.service;

import com.example.mobilele.model.view.StatsViewModel;

public interface StatsService {
  void onRequest(String path,
                 String username,
                 boolean authenticated,
                 int status,
                 long durationMs);

  StatsViewModel getStats();

  void resetStats();
}
