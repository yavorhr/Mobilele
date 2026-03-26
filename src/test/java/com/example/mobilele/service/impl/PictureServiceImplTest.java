package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;
import com.example.mobilele.repository.PictureRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

  @Test
  void testFindById_NotFound() {
    when(pictureRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class, () -> {
      pictureService.findById(1L);
    });
  }

  @Test
  void testAddPicturesToOffer_Success() throws IOException {
    MultipartFile file = mock(MultipartFile.class);
    when(file.getOriginalFilename()).thenReturn("test.jpg");

    CloudinaryImage uploaded = new CloudinaryImage();
    uploaded.setUrl("http://image.url");
    uploaded.setPublicId("public-id");

    when(cloudinaryService.upload(file, "api")).thenReturn(uploaded);

    PicturesAddServiceModel serviceModel = new PicturesAddServiceModel();
    serviceModel.setPictures(List.of(file));
    serviceModel.setOfferId(1L);
    serviceModel.setUserId(2L);

    when(offerService.findById(1L)).thenReturn(mock());
    when(userService.findById(2L)).thenReturn(mock());

    pictureService.addPicturesToOffer(serviceModel);

    verify(pictureRepository, times(1)).saveAll(anyList());
  }

  @Test
  void testAddPicturesToOffer_UploadFails() throws IOException {
    MultipartFile file = mock(MultipartFile.class);

    when(cloudinaryService.upload(file, "api")).thenReturn(null);

    PicturesAddServiceModel serviceModel = new PicturesAddServiceModel();
    serviceModel.setPictures(List.of(file));

    assertThrows(RuntimeException.class, () -> {
      pictureService.addPicturesToOffer(serviceModel);
    });

    verify(pictureRepository, never()).saveAll(anyList());
  }

  @Test
  void testDeleteByPublicId() {
    pictureService.deleteByPublicId("test-id");

    verify(pictureRepository, times(1)).deleteByPublicId("test-id");
  }
}
