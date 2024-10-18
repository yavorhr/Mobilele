package com.example.mobilele.repository;
import com.example.mobilele.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity,Long> {

  List<OfferEntity> findAllByModel_BrandName(String brandName);
}
