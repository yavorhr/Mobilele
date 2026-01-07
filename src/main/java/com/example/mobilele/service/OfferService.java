package com.example.mobilele.service;

import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.enums.VehicleCategoryEnum;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.service.offer.OfferUpdateServiceModel;
import com.example.mobilele.model.service.offer.OffersFindServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.view.user.TopSellerViewModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

public interface OfferService {

  void initOffers();

  Page<OfferBaseViewModel> findAllOffers(Pageable pageable);

  OfferViewModel findOfferById(String name, Long id);

  void deleteById(Long id);

  OfferAddServiceModel addOffer(OfferAddServiceModel offer, String username) throws IOException;

  Collection<OfferViewModel> findOffersByBrandAndVehicleType(String brand, VehicleCategoryEnum vehicleType);

  void updateOffer(OfferUpdateServiceModel serviceModel, Long id);

  Page<OfferBaseViewModel> findOffersByFilters(OffersFindServiceModel offersFindServiceModel, VehicleCategoryEnum vehicleCategory, Pageable pageable);

  Page<OfferBaseViewModel> findByTypeBrandAndModel(VehicleCategoryEnum category, String brand, String modelName, Pageable pageable);

  OfferEntity findById(long id);

  List<OfferBaseViewModel> findOffersByBrand(String toUpperCase);

  Page<OfferBaseViewModel> findOffersByUserId(String username, Pageable pageable);

  List<OfferBaseViewModel> findLatestOffers(int count);

  List<OfferBaseViewModel> findMostViewedOffers(int count);

  boolean doesOfferExistInUsersFavorites(Long id, String name);

  Page<OfferBaseViewModel> findFavoriteOffers(String username, Pageable pageable);

  boolean toggleReservation(Long id, String username);

  void incrementViewsIfEligible(Long id, HttpServletRequest request, Principal principal);

  List<OfferBaseViewModel> findTopOffersByViews();

  void saveSoldOffer(Long id);

  long getSoldVehiclesCount();

  List<TopSellerViewModel> getTop20Sellers();

  List<TopSellerViewModel> getSellerPerformanceByYear(int year, Integer top);
}
