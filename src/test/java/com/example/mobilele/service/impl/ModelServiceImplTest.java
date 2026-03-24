package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ModelServiceImplTest {

  @Mock
  private ModelRepository modelRepository;

  @Mock
  private BrandService brandService;

  @InjectMocks
  private ModelServiceImpl modelService;

  private ModelEntity model;

  @BeforeEach
  void init() {
    model = new ModelEntity();
    model.setId(1L);
    model.setName("X5");
  }

  @Test
  void testFindById() {

    when(modelRepository.findById(1L)).thenReturn(Optional.of(model));

    Optional<ModelEntity> result = modelService.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(model, result.get());
  }

  @Test
  void testFindByName_Success() {
    when(modelRepository.findByName("X5")).thenReturn(Optional.of(model));

    ModelEntity result = modelService.findByName("X5");

    assertEquals(model, result);
    assertEquals("X5", result.getName());
  }

  @Test
  void testFindByName_NotFound() {
    when(modelRepository.findByName("N/A")).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> modelService.findByName("N/A"));
  }

  @Test
  void testFindModelsByVehicleTypeAndBrand() {
    when(modelRepository.findAllByBrandNameAndVehicleType("BMW", VehicleCategoryEnum.SUV))
            .thenReturn(List.of("X5", "X3"));

    List<String> result =
            modelService.findModelsByVehicleTypeAndBrand("BMW", VehicleCategoryEnum.SUV);

    assertEquals(List.of("X5", "X3"), result);
  }
}
