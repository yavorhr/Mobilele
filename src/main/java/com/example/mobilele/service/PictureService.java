package com.example.mobilele.service;

import com.example.mobilele.model.entity.Picture;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PictureService {

  List<Picture> addOfferPictures(List<MultipartFile> pictures) throws IOException;

  Picture findById(Long id);
}
