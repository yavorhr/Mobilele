package com.example.mobilele.service;

import com.example.mobilele.model.entity.Picture;
import com.example.mobilele.model.service.offer.PictureAddServiceModel;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;

import java.io.IOException;

public interface PictureService {

//  List<Picture> addOfferPictures(List<MultipartFile> pictures) throws IOException;

  Picture findById(Long id);

  void addPicturesToOffer(PicturesAddServiceModel serviceModel) throws IOException;

  void deleteByPublicId(String publicId);

}
