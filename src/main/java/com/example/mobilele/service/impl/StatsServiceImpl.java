package com.example.mobilele.service.impl;

import com.example.mobilele.model.dto.view.StatsView;
import com.example.mobilele.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
  private int anonymousRequests, authRequests;

  @Override
  public void onRequest() {
    Authentication authentication = SecurityContextHolder.
        getContext().
        getAuthentication();

    if (authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
      authRequests++;
    } else {
      anonymousRequests++;
    }
  }

  @Override
  public StatsView getStats() {
    return new StatsView(authRequests, anonymousRequests);
  }
}
