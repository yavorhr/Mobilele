package com.example.mobilele.repository;

import com.example.mobilele.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

//  @Query("SELECT p.url FROM Picture as p")
//  List<String> getAllPictureUrls();
//
  void deleteByPublicId(String publicId);
}
