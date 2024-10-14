package com.example.mobilele.web;

import com.example.mobilele.service.ModelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModelController {
  private final ModelService modelService;

  public ModelController(ModelService modelService) {
    this.modelService = modelService;
  }

  @GetMapping("/models")
  public List<String> getModels(@RequestParam String brand) {
    return this.modelService.findModelsPerBrand(brand);
  }
}
