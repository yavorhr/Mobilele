package com.example.mobilele.service.impl;

import com.example.mobilele.init.OfferSeedGenerator;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

  @Mock
  private OfferRepository offerRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private SoldOfferRepository soldOfferRepository;
  @Mock
  private ModelService modelService;
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private UserService userService;
  @Mock
  private BrandService brandService;
  @Mock
  private CloudinaryService cloudinaryService;
  @Mock
  private OfferSeedGenerator seedGenerator;

  @InjectMocks
  private OfferServiceImpl offerService;

  OfferEntity offer;

  @BeforeEach
  void init(){
    offer = new OfferEntity();
    offer.setId(1L);
    offer.setReserved(true);
  }

  @Test
  void testFindOfferById() {
    OfferViewModel vm = new OfferViewModel();

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
    when(modelMapper.map(offer, OfferViewModel.class)).thenReturn(vm);
    when(userService.isOwnerOrIsAdmin("user", 1L)).thenReturn(true);
    when(userService.isNotOwnerOrIsAdmin("user", 1L)).thenReturn(false);

    OfferViewModel result = offerService.findOfferById("user", 1L);

    assertTrue(result.isCanModify());
    assertFalse(result.isNotOwnerOrIsAdmin());
    assertTrue(result.isReserved());
  }

  @Test
  void testFindOfferById_NotFound() {
    when(offerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> offerService.findOfferById("user", 1L));
  }

  @Test
  void testDeleteById() {
    OfferEntity offer = new OfferEntity();
    offer.setId(1L);

    Picture pic = new Picture();
    pic.setPublicId("pic1");

    offer.setPictures(List.of(pic));

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    offerService.deleteById(1L);

    verify(userRepository).deleteFavoritesByOfferId(1L);
    verify(cloudinaryService).delete("pic1");
    verify(offerRepository).delete(offer);
  }

  @Test
  void testDeleteById_NotFound() {
    when(offerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> offerService.deleteById(1L));
  }
}
