package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.model.service.brand.BrandServiceModel;
import com.example.mobilele.model.view.brand.BrandViewNameModel;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

  @Mock
  private BrandRepository brandRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private BrandServiceImpl brandService;

  BrandEntity bmw = new BrandEntity();
  BrandEntity audi = new BrandEntity();

  @BeforeEach
  void init() {
    bmw = new BrandEntity();
    bmw.setName("BMW");

    audi = new BrandEntity();
    audi.setName("AUDI");
  }

  @Test
  void testFindBrandByName_success() {
    // Arrange
    when(brandRepository.findBrandByNameIgnoreCase("BMW"))
            .thenReturn(Optional.of(bmw));

    // Act
    BrandEntity result = brandService.findBrandByName("BMW");

    // Assert
    Assertions.assertEquals("BMW", result.getName());
  }

  @Test
  void testFindBrandByName_notFound() {
    when(brandRepository
            .findBrandByNameIgnoreCase("none_existing_brand"))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(ObjectNotFoundException.class, () -> brandService.findBrandByName("none_existing_brand"));
  }

  @Test
  void testFindAllBrands() {
    BrandViewNameModel viewModelAudi = new BrandViewNameModel();
    viewModelAudi.setName("Audi");

    BrandViewNameModel viewModelBmw = new BrandViewNameModel();
    viewModelBmw.setName("BMW");

    when(brandRepository.findAll()).thenReturn(List.of(audi, bmw));

    when(modelMapper.map(audi, BrandViewNameModel.class))
            .thenReturn(viewModelAudi);

    when(modelMapper.map(bmw, BrandViewNameModel.class))
            .thenReturn(viewModelBmw);

    List<BrandViewNameModel> result = brandService.findAllBrands();

    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("Audi", result.get(0).getName());
    Assertions.assertEquals("BMW", result.get(1).getName());
  }

  @Test
  void testGetAllBrands() {
    BrandServiceModel serviceModel = new BrandServiceModel();
    serviceModel.setName("BMW");

    when(brandRepository.findAllBrandsWithModels())
            .thenReturn(List.of(bmw));

    when(modelMapper.map(bmw, BrandServiceModel.class))
            .thenReturn(serviceModel);

    var result = brandService.getAllBrands();

    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals("BMW", result.iterator().next().getName());
  }

  @Test
  void testSeedBrands_whenAlreadySeeded_shouldDoNothing() {
    when(brandRepository.count()).thenReturn(5L);

    brandService.seedBrands();

    Mockito.verify(brandRepository, Mockito.never()).saveAll(Mockito.any());
  }

  @Test
  void testSeedBrands_whenEmpty_shouldSeed() {
    when(brandRepository.count()).thenReturn(0L);

    brandService.seedBrands();

    Mockito.verify(brandRepository, Mockito.times(1)).saveAll(Mockito.any());
  }
}
