package com.example.mobilele.repository;

import com.example.mobilele.model.entity.Picture;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
  
  @Transactional
  @Modifying
  @Query("DELETE FROM Picture p WHERE p.publicId = :publicId")
  void deleteByPublicId(@Param("publicId") String publicId);

}
