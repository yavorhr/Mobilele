package com.example.mobilele.service;

import com.example.mobilele.model.view.admin.StatsViewModel;

public interface StatsService {
  void onRequest(String path,
                 String username,
                 boolean authenticated,
                 int status,
                 long durationMs);

  StatsViewModel getStats();

  void resetStats();

  void saveSnapshot(StatsViewModel stats);
}
