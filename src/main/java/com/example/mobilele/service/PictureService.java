package com.example.mobilele.service;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.service.offer.PictureAddServiceModel;

public interface PictureService {

//  List<Picture> addOfferPictures(List<MultipartFile> pictures) throws IOException;

  Picture findById(Long id);

  void addOfferPictures(PictureAddServiceModel serviceModel);

  void deleteByPublicId(String publicId);
}
