package com.example.mobilele.web;

import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.service.ModelService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ModelControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ModelService modelService;

  // =========================
  // 🔹 SUCCESS CASE
  // =========================

  @Test
  void getModels_shouldReturnModelsList() throws Exception {

    when(modelService.findModelsByVehicleTypeAndBrand(
            "BMW",
            VehicleCategoryEnum.Car
    )).thenReturn(List.of("X5", "X6"));

    mockMvc.perform(get("/models")
            .param("brand", "bmw")
            .param("vehicleType", "CAR"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0]").value("X5"))
            .andExpect(jsonPath("$[1]").value("X6"));
  }

  @Test
  void getModels_shouldReturnEmptyList() throws Exception {

    when(modelService.findModelsByVehicleTypeAndBrand(
            "AUDI",
            VehicleCategoryEnum.Car
    )).thenReturn(List.of());

    mockMvc.perform(get("/models")
            .param("brand", "audi")
            .param("vehicleType", "CAR"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
  }
}
