package com.example.mobilele.service;

import com.example.mobilele.model.binding.offer.OfferUpdateBindingForm;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface OfferService {

  void seedOffers();

  Page<OfferBaseViewModel> findAllOffers(Pageable pageable);

  OfferViewModel findOfferById(String name, Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, String username) throws IOException;

  void updateOffer(OfferUpdateServiceModel serviceModel, Long id);

  Page<OfferBaseViewModel> findOffersByFilters(OffersFindServiceModel offersFindServiceModel, VehicleCategoryEnum vehicleCategory, Pageable pageable);

  Page<OfferBaseViewModel> findByTypeBrandAndModel(VehicleCategoryEnum category, String brand, String modelName, Pageable pageable);

  OfferEntity findById(long id);

  Page<OfferBaseViewModel> findOffersByBrand(String brandName, Pageable pageable);

  Page<OfferBaseViewModel> findOffersByUserId(String username, Pageable pageable);

  List<OfferBaseViewModel> findLatestOffers(int count);

  List<OfferBaseViewModel> findMostViewedOffers(int count);

  void incrementViews(Long offerId);

  List<OfferBaseViewModel> findTopOffersByViews();

  OfferUpdateBindingForm getUpdateForm(Long id);

  List<TopSellerViewModel> getTop20Sellers();

  Page<TopSellerViewModel> getSellerPerformanceByYear(int year, int page);



}
