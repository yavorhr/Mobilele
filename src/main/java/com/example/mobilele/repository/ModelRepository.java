package com.example.mobilele.repository;

import com.example.mobilele.model.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

  @Query("SELECT m.name FROM ModelEntity m WHERE m.brand.name = :brandName")
  List<String> findAllByBrandName(String brandName);

  Optional<ModelEntity> findByName(String name);
}
