package com.example.mobilele.repository;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity,Long>, JpaSpecificationExecutor<OfferEntity> {

  List<OfferEntity> findAllByModel_Brand_NameAndModel_VehicleType(String brandName, VehicleCategoryEnum vehicleType);

  List<OfferEntity> findAllByModel_Brand_Name(String brandName);

  List<OfferEntity> findAllByModel_VehicleTypeAndModel_Brand_NameAndModel_Name(VehicleCategoryEnum category, String brand, String model);
}
