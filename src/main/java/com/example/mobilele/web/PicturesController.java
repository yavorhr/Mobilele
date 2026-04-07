package com.example.mobilele.web;

import com.example.mobilele.model.binding.offer.PicturesAddBindingModel;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

  @PreAuthorize("@security.canModifyOffer(#principal.username, #offerId)")
  @PostMapping("/pictures")
  public String addOfferPictures(PicturesAddBindingModel bindingModel,
                                 @RequestParam("offerId") Long offerId,
                                 @AuthenticationPrincipal MobileleUser principal) throws IOException {

    bindingModel.setOfferId(offerId);

    PicturesAddServiceModel serviceModel = this.modelMapper.map(bindingModel, PicturesAddServiceModel.class);
    serviceModel.setUserId(principal.getId());

    this.pictureService.addPicturesToOffer(serviceModel);

    return "redirect:/offers/details/" + bindingModel.getOfferId();
  }

  @PreAuthorize("@security.canModifyOffer(#principal.username, #offerId)")
  @DeleteMapping("/pictures")
  @ResponseBody
  public ResponseEntity<Void> deletePicture(@RequestParam("publicId") String publicId,
                                            @RequestParam("offerId") Long offerId,
                                            @AuthenticationPrincipal MobileleUser principal) {

    boolean cloudinaryDeleted = this.cloudinaryService.delete(publicId);
    if (!cloudinaryDeleted) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Could not delete picture from Cloudinary");
    }

    pictureService.deleteByPublicId(publicId);

    return ResponseEntity.noContent().build();
  }
}
