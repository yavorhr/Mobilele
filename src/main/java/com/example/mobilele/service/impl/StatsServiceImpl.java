package com.example.mobilele.service.impl;

import com.example.mobilele.model.view.StatsViewModel;
import com.example.mobilele.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

  @Override
  public void onRequest(String path, String username, boolean authenticated, int status, long durationMs) {

  }

  @Override
  public StatsViewModel getStats() {
    return null;
  }

  @Override
  public void resetStats() {

  }
}
