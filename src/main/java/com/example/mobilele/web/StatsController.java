package com.example.mobilele.web;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class StatsController {
  private final StatsService statsService;

  public StatsController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping("/statistics")
  public String statistics(Model model) {
    model.addAttribute("stats", statsService.getStats());
    return "admin/live-stats";
  }

  @PostMapping("/statistics")
  public String saveStatsSnapshot(RedirectAttributes redirectAttributes) {
    StatsViewModel stats = statsService.getStats();

    statsService.saveSnapshot(stats);
    statsService.resetStats();

    redirectAttributes.addFlashAttribute("message", "Stats snapshot saved and counters reset.");
    return "redirect:/admin/statistics";
  }

  @GetMapping("/history")
  public String statsHistory(Model model) {
    model.addAttribute("snapshots", statsService.getAllSnapshots());
    return "/admin/history";
  }


  @GetMapping("/history/{id}")
  public String showSnapshotDetails(@PathVariable Long id, Model model) {
    StatsViewModel stats = statsService.getSnapshotViewById(id);
    model.addAttribute("stats", stats);
    return "admin/snapshot-details";
  }
}
