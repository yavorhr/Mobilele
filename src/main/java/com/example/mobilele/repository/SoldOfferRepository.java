package com.example.mobilele.repository;

import com.example.mobilele.model.entity.SoldOfferEntity;
import com.example.mobilele.model.view.offer.SoldOfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
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

  @Query("""
          SELECT so.seller.firstName AS firstName,
          so.seller.lastName AS lastName,
          so.seller.email AS email,
          so.seller.phoneNumber AS phoneNumber,
          COUNT(so.id) AS soldCount
          FROM SoldOfferEntity so
          WHERE so.saleTime >= :start AND so.saleTime < :end
          GROUP BY so.seller.firstName, so.seller.lastName, so.seller.email, so.seller.phoneNumber
          ORDER BY COUNT(so.id) DESC
          """)
  List<TopSellerViewModel> findSellerPerformanceByPeriod(
          @Param("start") Instant start,
          @Param("end") Instant end
  );

  @Modifying
  @Query("UPDATE SoldOfferEntity s SET s.seller = null WHERE s.seller.id = :userId")
  void clearSeller(@Param("userId") Long userId);

  @Query("""
    SELECT new com.example.mobilele.model.view.offer.SoldOfferViewModel(
        s.id,
        CASE 
            WHEN u.id IS NULL THEN 'User deleted'
            ELSE CONCAT(u.firstName, ' ', u.lastName)
        END,
        u.id,
        u.email,
        u.phoneNumber,
        m.name,
        b.name,
        s.country,
        s.city,
        s.price,
        s.mileage,
        s.engine,
        s.condition,
        s.transmission,
        s.color,
        s.views,
        s.saleTime
    )
    FROM SoldOfferEntity s
    JOIN s.model m
    JOIN m.brand b
    LEFT JOIN s.seller u
    WHERE s.saleTime >= :start AND s.saleTime < :end
    ORDER BY s.saleTime DESC
""")
  Page<SoldOfferViewModel> findSoldCarsByPeriod(
          @Param("start") Instant start,
          @Param("end") Instant end,
          Pageable pageable);
}

