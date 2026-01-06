package com.example.mobilele.repository;

import com.example.mobilele.model.entity.SoldOfferEntity;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SoldOfferRepository extends JpaRepository<SoldOfferEntity, Long> {

  @Query("""
    SELECT
        u.firstName AS firstName,
        u.lastName AS lastName,
        u.email AS email,
        u.phoneNumber AS phoneNumber,
        COUNT(o.id) AS soldCount
    FROM SoldOfferEntity o
    JOIN o.seller u
    GROUP BY u.id, u.firstName, u.lastName, u.email, u.phoneNumber
    ORDER BY COUNT(o.id) DESC
""")
  List<TopSellerViewModel> findTop20Sellers(Pageable pageable);
}
