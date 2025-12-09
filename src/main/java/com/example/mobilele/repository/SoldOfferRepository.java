package com.example.mobilele.repository;

import com.example.mobilele.model.entity.SoldOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldOfferRepository extends JpaRepository<SoldOfferEntity, Long> {

}
