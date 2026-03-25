package com.example.mobilele.service.impl;

import com.example.mobilele.init.OfferSeedContext;
import com.example.mobilele.init.OfferSeedGenerator;
import com.example.mobilele.model.entity.*;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.BrandService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

  @Test
  void testAddOffer() throws IOException {
    //Arrange
    OfferAddServiceModel serviceModel = new OfferAddServiceModel();
    serviceModel.setModel("X5");
    serviceModel.setBrand("BMW");

    MultipartFile file = mock(MultipartFile.class);
    when(file.getOriginalFilename()).thenReturn("test.jpg");
    serviceModel.setPictures(List.of(file));

    ModelEntity model = new ModelEntity();
    BrandEntity brand = new BrandEntity();
    UserEntity user = new UserEntity();

    OfferEntity offer = new OfferEntity();
    offer.setPictures(new ArrayList<>());

    when(modelMapper.map(serviceModel, OfferEntity.class)).thenReturn(offer);
    when(modelService.findByName("X5")).thenReturn(model);
    when(brandService.findBrandByName("BMW")).thenReturn(brand);
    when(userService.findByUsername("user")).thenReturn(user);

    CloudinaryImage image = new CloudinaryImage();
    image.setUrl("url");
    image.setPublicId("id");

    when(cloudinaryService.upload(file, "api")).thenReturn(image);

    when(offerRepository.save(any())).thenAnswer(inv -> {
      OfferEntity o = inv.getArgument(0);
      o.setId(1L);
      return o;
    });

    OfferAddServiceModel result =
            offerService.addOffer(serviceModel, "user");

    assertEquals(1L, result.getId());
    verify(cloudinaryService).upload(file, "api");
  }

  @Test
  void testUpdateOffer() {
    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    OfferUpdateServiceModel model = new OfferUpdateServiceModel();

    offerService.updateOffer(model, 1L);

    verify(modelMapper).map(model, offer);
    verify(offerRepository).save(offer);
  }

  @Test
  void testIncrementViews() {
    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    offerService.incrementViews(1L);

    verify(offerRepository).incrementViews(1L);
  }

  @Test
  void testIncrementViews_NotFound() {
    when(offerRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> offerService.incrementViews(1L));
  }

  @Test
  void testSeedOffers() {
    when(offerRepository.count()).thenReturn(0L);

    OfferSeedContext context = mock(OfferSeedContext.class);

    ModelEntity model = new ModelEntity();
    BrandEntity brand = new BrandEntity();
    brand.setName("BMW");
    model.setBrand(brand);
    model.setName("X5");

    when(context.model()).thenReturn(model);
    when(context.seller()).thenReturn(new UserEntity());

    when(seedGenerator.generateData()).thenReturn(List.of(context));

    when(cloudinaryService.buildImageUrl(any()))
            .thenReturn("url");

    offerService.seedOffers();

    verify(offerRepository).saveAll(argThat(iterable -> {
      int count = 0;
      for (Object ignored : iterable) count++;
      return count == 1;
    }));
  }

  @Test
  void testSeedOffers_ShouldNotSeed() {
    when(offerRepository.count()).thenReturn(5L);

    offerService.seedOffers();

    verify(offerRepository, never()).saveAll(any());
  }

  @Test
  void testFindById(){
    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    OfferEntity result = offerService.findById(1L);

    assertEquals(1L, result.getId());
  }

  @Test
  void testFindById_NotFound(){
    when(offerRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(ObjectNotFoundException.class, ()-> offerService.findById(1L));
  }

  @Test
  void testFindOffersByBrand() {
    offer.setPictures(List.of(new Picture() {{ setUrl("img.jpg"); }}));

    offer.setModel(new ModelEntity() {{ setName("X5"); }});

    Pageable pageable = PageRequest.of(0, 5);

    when(offerRepository.findAllByModel_Brand_Name("BMW", pageable))
            .thenReturn(new PageImpl<>(List.of(offer)));

    when(modelMapper.map(any(), eq(OfferBaseViewModel.class)))
            .thenReturn(new OfferBaseViewModel());

    Page<OfferBaseViewModel> result =
            offerService.findOffersByBrand("BMW", pageable);

    assertEquals(1, result.getContent().size());
    verify(offerRepository).findAllByModel_Brand_Name("BMW", pageable);
  }

  @Test
  void testFindMostViewedOffers() {
    offer.setPictures(List.of(new Picture() {{ setUrl("img.jpg"); }}));

    offer.setModel(new ModelEntity() {{
      setName("X5"); }});

    when(offerRepository.findAllByOrderByViewsDesc(any(PageRequest.class)))
            .thenReturn(List.of(offer));

    when(modelMapper.map(any(), eq(OfferBaseViewModel.class)))
            .thenReturn(new OfferBaseViewModel());

    List<OfferBaseViewModel> result =
            offerService.findMostViewedOffers(5);

    assertEquals(1, result.size());
  }

  @Test
  void testFindLatestOffers() {
    offer.setPictures(List.of(new Picture() {{ setUrl("img.jpg"); }}));

    offer.setModel(new ModelEntity() {{ setName("X5"); }});

    when(offerRepository.findAllByOrderByCreatedDesc(any(PageRequest.class)))
            .thenReturn(List.of(offer));

    when(modelMapper.map(any(), eq(OfferBaseViewModel.class)))
            .thenReturn(new OfferBaseViewModel());

    List<OfferBaseViewModel> result =
            offerService.findLatestOffers(5);

    assertEquals(1, result.size());
  }
}
