package com.example.mobilele.web;

import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;

  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/notifications")
  public String viewNotifications(
          @RequestParam(defaultValue = "") String query,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "5") int size,
          Model model,
          @AuthenticationPrincipal UserDetails principal) {

    Page<UserAdministrationViewModel> usersPage = this.userService.searchPaginatedUsersPerEmail(query, PageRequest.of(page, size));

    model.addAttribute("usersPage", usersPage);
    model.addAttribute("users", usersPage.getContent());
    model.addAttribute("loggedInUserEmail", principal.getUsername());
    model.addAttribute("query", query);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", usersPage.getTotalPages());

    return "/admin/notifications";
  }

  @PutMapping("/api/change-user-access/{email}")
  @ResponseBody
  public ResponseEntity<UserUpdateStatusResponse> changeUserAccess(@PathVariable String email,
                                                                   @AuthenticationPrincipal UserDetails principal) {

    UserUpdateStatusResponse statusResponse = this.userService.changeAccess(email);

    return ResponseEntity.ok(statusResponse);
  }
}
