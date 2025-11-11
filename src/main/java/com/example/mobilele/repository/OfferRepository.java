package com.example.mobilele.repository;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>, JpaSpecificationExecutor<OfferEntity> {

  List<OfferEntity> findAllByModel_Brand_NameAndModel_VehicleType(String brandName, VehicleCategoryEnum vehicleType);

  List<OfferEntity> findAllByModel_Brand_Name(String brandName);

  Page<OfferEntity> findAllByModel_VehicleTypeAndModel_Brand_NameAndModel_Name(
          VehicleCategoryEnum vehicleType,
          String brand,
          String modelName,
          Pageable pageable);

  Page<OfferEntity> findAllBySeller_Username(String username, Pageable pageable);

  List<OfferEntity> findAllByOrderByCreatedDesc(Pageable pageable);

  List<OfferEntity> findAllByOrderByViewsDesc(Pageable pageable);

  @Query("SELECT o FROM OfferEntity o JOIN o.favoritedBy u WHERE u.username = :username")
  Page<OfferEntity> findFavoritesByUsername(@Param("username") String username, Pageable pageable);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("UPDATE OfferEntity o SET o.views = o.views + 1 WHERE o.id = :offerId")
  void incrementViews(@Param("offerId") Long offerId);

  List<OfferEntity> findTop20ByOrderByViewsDesc();
}
