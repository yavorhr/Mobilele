package com.example.mobilele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OffersController {


  @GetMapping("/offers/all")
  public String getAllOffersPage() {
    return "offers";
  }

  @GetMapping("/offers/add")
  public String getAddOffersPage(){
    return "offer-add";
  }
}
