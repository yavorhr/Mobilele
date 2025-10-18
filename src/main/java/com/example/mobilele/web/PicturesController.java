package com.example.mobilele.web;

import com.example.mobilele.model.binding.PictureDeleteRequest;
import com.example.mobilele.model.binding.offer.PicturesAddBindingModel;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.impl.principal.MobileleUser;
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
  public String addOfferPictures(PicturesAddBindingModel bindingModel,
                                 @AuthenticationPrincipal MobileleUser principal) throws IOException {

    PicturesAddServiceModel serviceModel = this.modelMapper.map(bindingModel, PicturesAddServiceModel.class);
    serviceModel.setUserId(principal.getId());

    this.pictureService.addPicturesToOffer(serviceModel);

    return "redirect:/offers/details/" + bindingModel.getOfferId();
  }

  // Add @Preauthrorize check
  @DeleteMapping("/pictures")
  @ResponseBody
  public ResponseEntity<Void> deletePicture(@RequestBody PictureDeleteRequest request) {

    pictureService.deleteByPublicId(request.getPublic_id());

    return ResponseEntity.noContent().build();
  }
}
