package com.example.mobilele.repository;

import com.example.mobilele.model.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<BrandEntity, Long> {
}
