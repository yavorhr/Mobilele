package com.example.mobilele.web;

import com.example.mobilele.model.view.admin.StatsViewModel;
import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.StatsService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class StatsController {
  private final StatsService statsService;
  private final OfferService offerService;

  public StatsController(StatsService statsService, OfferService offerService) {
    this.statsService = statsService;
    this.offerService = offerService;
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

  @GetMapping("/sellers-performance")
  public String getTopSellersByYear(
          @RequestParam(required = false) Integer year,
          @RequestParam(required = false) Integer top,
          Model model) {

    int selectedYear = (year != null) ? year : Year.now().getValue();
    int topN = (top != null) ? top : 20;

    List<TopSellerViewModel> sellers =
            offerService.getSellerPerformanceByYear(selectedYear, topN);

    model.addAttribute("selectedYear", selectedYear);
    model.addAttribute("top", topN);
    model.addAttribute("sellers", sellers);

    int currentYear = Year.now().getValue();

    model.addAttribute("years",
            IntStream.rangeClosed(currentYear - 4, currentYear)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .toList()
    );

    return "admin/sellers-performance";
  }

  @GetMapping("/sold-cars-stats")
  public String getSoldCarsPerYear(
          @RequestParam(required = false) Integer year,
          @RequestParam(defaultValue = "0") int page,
          Model model) {

    int selectedYear = (year != null) ? year : Year.now().getValue();

    Page<SoldOfferViewModel> carsPage =
            offerService.getSoldCarsByYear(selectedYear, page);

    model.addAttribute("selectedYear", selectedYear);
    model.addAttribute("cars", carsPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", carsPage.getTotalPages());

    int currentYear = Year.now().getValue();

    model.addAttribute("years",
            IntStream.rangeClosed(currentYear - 4, currentYear)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .toList());

    return "admin/sold-cars-year";
  }
}

