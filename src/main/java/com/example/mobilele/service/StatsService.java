package com.example.mobilele.service;

import com.example.mobilele.model.dto.view.StatsView;

public interface StatsService {
  void onRequest();
  StatsView getStats();
}
