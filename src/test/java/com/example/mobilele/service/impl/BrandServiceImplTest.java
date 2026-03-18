package com.example.mobilele.service.impl;


import com.example.mobilele.model.entity.BrandEntity;
import com.example.mobilele.repository.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
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

}
