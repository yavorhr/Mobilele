package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;
import com.example.mobilele.repository.PictureRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
  private final PictureRepository pictureRepository;
  private final OfferService offerService;
  private final UserService userService;
  private final CloudinaryService cloudinaryService;

  public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository, OfferService offerService, UserService userService, CloudinaryService cloudinaryService1) {
    this.pictureRepository = pictureRepository;
    this.offerService = offerService;
    this.userService = userService;
    this.cloudinaryService = cloudinaryService1;
  }

  @Override
  public Picture findById(Long id) {
    return this.pictureRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Picture with id" + id + "does not exist!"));
  }

  @Override
  public void addPicturesToOffer(PicturesAddServiceModel serviceModel) throws IOException {

    List<Picture> uploadedPictures = new ArrayList<>();

    for (MultipartFile file : serviceModel.getPictures()) {
      CloudinaryImage uploaded = cloudinaryService.upload(file, "cars-offers");

      if (uploaded == null || uploaded.getUrl() == null || uploaded.getUrl().isBlank()) {
        throw new RuntimeException("Image upload failed! ");
      }

      Picture picture = mapToPicture(uploaded.getUrl(), uploaded.getPublicId(), convertTitle(file.getOriginalFilename()));

      picture.setOffer(this.offerService.findById(serviceModel.getOfferId()));
      picture.setSeller(this.userService.findById(serviceModel.getUserId()));

      uploadedPictures.add(picture);
    }

    this.pictureRepository.saveAll(uploadedPictures);
  }

  @Override
  @Transactional
  public void deleteByPublicId(String publicId) {
    this.pictureRepository.deleteByPublicId(publicId);
  }

  // Helpers
  private Picture mapToPicture(String url, String publicId, String s) {
    Picture picture = new Picture();
    picture.setUrl(url);
    picture.setPublicId(publicId);
    picture.setTitle(s);
    return picture;
  }

  private String convertTitle(String originalName) {
    if (originalName != null) {
      int dotIndex = originalName.lastIndexOf('.');

      if (dotIndex > 0) {
        originalName = originalName.substring(0, dotIndex);
      }
    }
    return originalName;
  }
}
