package com.example.mobilele.service.impl;

import com.example.mobilele.init.OfferSeedGenerator;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.SoldOfferEntity;
import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SoldOfferServiceImplTest {

  @Mock
  private SoldOfferRepository soldOfferRepository;

  @Mock
  private OfferRepository offerRepository;

  @Mock
  private OfferSeedGenerator seedGenerator;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private SoldOfferServiceImpl soldOfferService;

  @Test
  void testSaveSoldOffer_success() {
    Long offerId = 1L;

    OfferEntity offerEntity = new OfferEntity();
    SoldOfferEntity mappedSold = new SoldOfferEntity();

    when(offerRepository.findById(offerId)).thenReturn(Optional.of(offerEntity));
    when(modelMapper.map(offerEntity, SoldOfferEntity.class)).thenReturn(mappedSold);

    soldOfferService.saveSoldOffer(offerId);

    verify(soldOfferRepository).save(mappedSold);

    assertNotNull(mappedSold.getSaleTime());
  }

  @Test
  void testSaveSoldOffer_notFound() {
    Long offerId = 1L;

    when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> soldOfferService.saveSoldOffer(offerId));
  }

  @Test
  void testGetSoldCarsByYear() {
    int year = 2024;
    int page = 0;

    Page<SoldOfferViewModel> mockPage = new PageImpl<>(List.of());

    when(soldOfferRepository.findSoldCarsByPeriod(any(), any(), any()))
            .thenReturn(mockPage);

    Page<SoldOfferViewModel> result =
            soldOfferService.getSoldCarsByYear(year, page);

    assertEquals(mockPage, result);

    verify(soldOfferRepository).findSoldCarsByPeriod(
            any(Instant.class),
            any(Instant.class),
            any(Pageable.class)
    );
  }
}
