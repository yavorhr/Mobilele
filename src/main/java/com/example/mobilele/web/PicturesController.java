package com.example.mobilele.web;

import com.example.mobilele.model.binding.PictureDeleteRequest;
import com.example.mobilele.model.binding.offer.PictureAddBindingModel;
import com.example.mobilele.model.service.offer.PictureAddServiceModel;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import com.example.mobilele.util.cloudinary.CloudinaryImage;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@Controller
public class PicturesController {
  private final CloudinaryService cloudinaryService;
  private final ModelMapper modelMapper;
  private final PictureService pictureService;

  public PicturesController(CloudinaryService cloudinaryService, ModelMapper modelMapper, PictureService pictureService) {
    this.cloudinaryService = cloudinaryService;
    this.modelMapper = modelMapper;
    this.pictureService = pictureService;
  }

  // Add @Preauthrorize check
  @PostMapping("/pictures")
  public String addOfferPictures(PictureAddBindingModel bindingModel,
                                 @AuthenticationPrincipal MobileleUser principal) throws IOException {

    CloudinaryImage uploaded = this.cloudinaryService.upload(bindingModel.getPicture(), "cars-offers");

    if (uploaded == null || uploaded.getUrl() == null || uploaded.getUrl().isBlank()) {
      throw new RuntimeException("Image upload failed! ");
    }

    PictureAddServiceModel serviceModel =
            mapToPictureServiceModel(uploaded, bindingModel, principal.getId());

    this.pictureService.addOfferPictures(serviceModel);

    return "redirect:/offers/details/" + bindingModel.getOfferId();
  }

  // Add @Preauthrorize check
  @DeleteMapping("/pictures")
  @ResponseBody
  public ResponseEntity<Void> deletePicture(@RequestBody PictureDeleteRequest request) {

    pictureService.deleteByPublicId(request.getPublic_id());

    return ResponseEntity.noContent().build();
  }

  // Helpers
  private PictureAddServiceModel mapToPictureServiceModel(CloudinaryImage image, PictureAddBindingModel bindingModel, Long userId) {
    PictureAddServiceModel serviceModel =
            this.modelMapper.map(bindingModel, PictureAddServiceModel.class);

    serviceModel.setOfferId(bindingModel.getOfferId());
    serviceModel.setUserId(userId);
    serviceModel.setPublicId(image.getPublicId());
    serviceModel.setUrl(image.getUrl());

    return serviceModel;
  }
}
