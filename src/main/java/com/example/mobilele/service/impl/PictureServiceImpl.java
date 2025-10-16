package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.service.offer.PictureAddServiceModel;
import com.example.mobilele.repository.PictureRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {
  private final PictureRepository pictureRepository;
  private final OfferService offerService;
  private final UserService userService;

  public PictureServiceImpl(CloudinaryService cloudinaryService, PictureRepository pictureRepository, OfferService offerService, UserService userService) {
    this.pictureRepository = pictureRepository;
    this.offerService = offerService;
    this.userService = userService;
  }

//  @Override
//  public List<Picture> addOfferPictures(List<MultipartFile> pictures) throws IOException {
//    List<Picture> uploadedPictures = new ArrayList<>();
//
//    for (MultipartFile file : pictures) {
//      CloudinaryImage uploaded = cloudinaryService.upload(file, "cars-offers");
//
//      Picture picture = new Picture();
//      picture.setUrl(uploaded.getUrl());
//      picture.setPublicId(uploaded.getPublicId());
//      picture.setTitle(convertTitle(file.getOriginalFilename()));
//    }
//
//    return uploadedPictures;
//  }

  @Override
  public Picture findById(Long id) {
    return this.pictureRepository
            .findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Picture with id" + id + "does not exist!"));
  }

  @Override
  public void addOfferPictures(PictureAddServiceModel serviceModel) {
    Picture picture = mapToPicture(serviceModel);
    this.pictureRepository.save(picture);
  }

  // Helpers
  private String convertTitle(String originalName) {
    if (originalName != null) {
      int dotIndex = originalName.lastIndexOf('.');

      if (dotIndex > 0) {
        originalName = originalName.substring(0, dotIndex);
      }
    }
    return originalName;
  }

  private Picture mapToPicture(PictureAddServiceModel serviceModel) {
    Picture picture = new Picture();

    picture.setUrl(serviceModel.getUrl());
    picture.setPublicId(serviceModel.getPublicId());
    picture.setTitle(serviceModel.getTitle());

    picture.setOffer(this.offerService.findById(serviceModel.getOfferId()));
    picture.setSeller(this.userService.findById(serviceModel.getUserId()));

    return picture;
  }
}
