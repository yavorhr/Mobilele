package com.example.mobilele.web;

import com.example.mobilele.model.dto.view.BrandViewModel;
import com.example.mobilele.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BrandController {
  private final BrandService brandService;
  private final ModelMapper modelMapper;

  public BrandController(BrandService brandService, ModelMapper modelMapper) {
    this.brandService = brandService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/brands/all")
  public String getBrandsPage(Model model){
    List<BrandViewModel> brandViewModels = brandService
            .getAllBrands()
            .stream()
            .map(brandServiceModel -> modelMapper.map(brandServiceModel, BrandViewModel.class))
            .collect(Collectors.toList());

    model.addAttribute("allBrands", brandViewModels);

    return "brands";
  }
}
