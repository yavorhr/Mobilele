package com.example.mobilele.repository;

import com.example.mobilele.model.entity.Feedback;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

  List<Feedback> findAllByOrderByCreatedDesc(Pageable pageable);

  @Query("SELECT AVG(f.rating) FROM Feedback f")
  Optional<Double> findAverageRating();
}
