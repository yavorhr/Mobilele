package com.example.mobilele.repository;

import com.example.mobilele.model.entity.ModelEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

  @Query("SELECT m.name FROM ModelEntity m WHERE m.brand.name = :brandName AND m.vehicleType = :vehicleType")
  List<String> findAllByBrandNameAndVehicleType(@Param("brandName") String brandName,
                                                @Param("vehicleType") VehicleCategoryEnum vehicleType);

  Optional<ModelEntity> findByName(String name);
}
