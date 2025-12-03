package com.example.mobilele.repository;

import com.example.mobilele.model.entity.StatsSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<StatsSnapshot, Long> {
}
