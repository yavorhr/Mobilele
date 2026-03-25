package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.repository.PictureRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

  @Mock
  private PictureRepository pictureRepository;

  @Mock
  private OfferService offerService;

  @Mock
  private UserService userService;

  @Mock
  private CloudinaryService cloudinaryService;

  @InjectMocks
  private PictureServiceImpl pictureService;

  @Test
  void testFindById_Success() {
    Picture picture = new Picture();
    picture.setId(1L);

    when(pictureRepository.findById(1L)).thenReturn(Optional.of(picture));

    Picture result = pictureService.findById(1L);

    assertEquals(1L, result.getId());
  }
}
