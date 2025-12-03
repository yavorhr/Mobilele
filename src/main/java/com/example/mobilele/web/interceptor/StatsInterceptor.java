package com.example.mobilele.web.interceptor;

import com.example.mobilele.service.StatsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class StatsInterceptor implements HandlerInterceptor {
  private static final String START_TIME_ATTR = "statsStartTime";
  private final StatsService statsService;

  public StatsInterceptor(StatsService statsService) {
    this.statsService = statsService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler) {
    request.setAttribute(START_TIME_ATTR, System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request,
                              HttpServletResponse response,
                              Object handler,
                              Exception ex) {

    Long startTime = (Long) request.getAttribute(START_TIME_ATTR);

    if (startTime == null) {
      return;
    }

    long durationMs = System.currentTimeMillis() - startTime;
    String path = request.getRequestURI();

    int status = response.getStatus();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    String username = "ANONYMOUS";
    boolean authenticated = false;

    if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {
      username = userDetails.getUsername();
      authenticated = true;
    }

    statsService.onRequest(path, username, authenticated, status, durationMs);
  }
}
