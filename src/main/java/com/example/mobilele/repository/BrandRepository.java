package com.example.mobilele.repository;

import com.example.mobilele.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

  Optional<Brand> findBrandByNameIgnoreCase(String name);
}
