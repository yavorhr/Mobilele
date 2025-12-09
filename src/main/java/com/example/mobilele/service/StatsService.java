package com.example.mobilele.service;

import com.example.mobilele.model.view.admin.StatsViewModel;

import java.util.List;

public interface StatsService {
  void onRequest(String path,
                 String username,
                 boolean authenticated,
                 int status,
                 long durationMs);

  StatsViewModel getStats();

  void resetStats();

  void saveSnapshot(StatsViewModel stats);

  List<StatsViewModel> getAllSnapshots();

  StatsViewModel getSnapshotViewById(Long id);
}
