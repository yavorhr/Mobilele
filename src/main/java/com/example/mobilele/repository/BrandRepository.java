package com.example.mobilele.repository;

import com.example.mobilele.model.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

  Optional<BrandEntity> findBrandByNameIgnoreCase(String name);

  @Query("SELECT b FROM BrandEntity b WHERE SIZE(b.models)>0 ORDER BY b.name ASC ")
  List<BrandEntity> findAllBrandsWithModels();
}
