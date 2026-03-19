package com.example.mobilele.service.impl;


import com.example.mobilele.model.entity.BrandEntity;
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
    Mockito.when(brandRepository.findBrandByNameIgnoreCase("BMW"))
            .thenReturn(Optional.of(bmw));

    // Act
    BrandEntity result = brandService.findBrandByName("BMW");

    // Assert
    Assertions.assertEquals("BMW", result.getName());
  }

  @Test
  void testFindBrandByName_notFound() {
    Mockito.when(brandRepository
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

    Mockito.when(brandRepository.findAll()).thenReturn(List.of(audi, bmw));

    Mockito.when(modelMapper.map(audi, BrandViewNameModel.class))
            .thenReturn(viewModelAudi);

    Mockito.when(modelMapper.map(bmw, BrandViewNameModel.class))
            .thenReturn(viewModelBmw);

    List<BrandViewNameModel> result = brandService.findAllBrands();

    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("Audi", result.get(0).getName());
    Assertions.assertEquals("BMW", result.get(1).getName());
  }

}
