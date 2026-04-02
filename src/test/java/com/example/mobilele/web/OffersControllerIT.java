package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.model.binding.offer.OfferUpdateBindingForm;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.enums.*;
import com.example.mobilele.model.service.PictureServiceModel;
import com.example.mobilele.model.service.offer.OfferAddServiceModel;
import com.example.mobilele.model.view.offer.OfferBaseViewModel;
import com.example.mobilele.model.view.offer.OfferViewModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.service.FavoritesService;
import com.example.mobilele.service.ModelService;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.SoldOfferService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OffersControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OfferService offerService;

  @MockBean
  private SoldOfferService soldOfferService;

  @MockBean
  private FavoritesService favoritesService;

  @MockBean
  private ModelService modelService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean(name = "security")
  private SecurityService securityService;

  // =========================
  // GET ALL OFFERS
  // =========================

    @Test
    void showAllOffers_shouldReturnOffersPage() throws Exception {

      Page<OfferBaseViewModel> page =
              new PageImpl<>(List.of(new OfferBaseViewModel()));

      when(offerService.findAllOffers(any()))
              .thenReturn(page);

      mockMvc.perform(get("/offers/all"))
            .andExpect(status().isOk())
            .andExpect(view().name("offers"))
            .andExpect(model().attributeExists("offers"))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attributeExists("totalPages"));
  }

  // =========================
  // TOP OFFERS
  // =========================

  @Test
  void showTopOffers_shouldReturnTopOffersPage() throws Exception {

    when(offerService.findTopOffersByViews())
            .thenReturn(List.of(new OfferBaseViewModel()));

    mockMvc.perform(get("/offers/top-offers"))
            .andExpect(status().isOk())
            .andExpect(view().name("top-offers"))
            .andExpect(model().attributeExists("offers"));
  }

  // =========================
  // OFFER DETAILS
  // =========================

  @Test
  void getOfferDetails_shouldWork() throws Exception {

    Long id = 1L;

    OfferViewModel offer = createValidOffer();

    when(offerService.findOfferById(any(), eq(id)))
            .thenReturn(offer);

    when(modelMapper.map(any(), eq(OfferViewModel.class)))
            .thenReturn(offer);

    mockMvc.perform(get("/offers/details/{id}", id))
            .andExpect(status().isOk())
            .andExpect(view().name("details"))
            .andExpect(model().attributeExists("offer"));
  }

  // =========================
  // ADD OFFER - GET
  // =========================

  @Test
  @WithMockUser
  void getAddOfferPage_shouldReturnAddView() throws Exception {

    mockMvc.perform(get("/offers"))
            .andExpect(status().isOk())
            .andExpect(view().name("add"));
  }

  // =========================
  // ADD OFFER - VALID
  // =========================

  @Test
  @WithMockUser(username = "user1", roles = {"USER"})
  void addOffer_shouldRedirectToDetails_whenValid() throws Exception {

    OfferAddServiceModel serviceModel = new OfferAddServiceModel();
    serviceModel.setId(1L);

    when(offerService.addOffer(any(), any()))
            .thenReturn(serviceModel);

    MockMultipartFile file =
            new MockMultipartFile("pictures", "test.jpg",
                    "image/jpeg", "content".getBytes());

    mockMvc.perform(multipart("/offers")
            .file(file)
            .param("description", "Very good car description")
            .param("price", "1000")
            .param("vehicleType", "Car")
            .param("model", "M3")
            .param("brand", "BMW")
            .param("condition", "Used")
            .param("engine", "Gasoline")
            .param("mileage", "100000")
            .param("transmission", "Manual")
            .param("year", "2020")
            .param("color", "Black")
            .param("country", "Bulgaria")
            .param("city", "Sofia")
            .with(authentication(createAuth("user1")))
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andDo(print())
            .andExpect(redirectedUrl("/offers/details/1"));
  }

  // =========================
  // ADD OFFER - NO PARAMS
  // =========================

  @Test
  void addOffer_shouldRedirectBack_whenNoImages() throws Exception {

    mockMvc.perform(multipart("/offers")
            .param("description", "Test")
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers"));
  }

  // =========================
  // UPDATE OFFER - GET
  // =========================

  @Test
  void getUpdateOffer_shouldReturnUpdateView_whenAuthorized() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(true);

    when(offerService.findOfferById(any(), eq(id)))
            .thenReturn(new OfferViewModel());

    when(modelMapper.map(any(), eq(OfferUpdateBindingForm.class)))
            .thenReturn(new OfferUpdateBindingForm());

    mockMvc.perform(get("/offers/update/{id}", id)
            .with(authentication(createAuth("user1"))))
            .andExpect(status().isOk())
            .andExpect(view().name("update"))
            .andExpect(model().attributeExists("offerUpdateBindingModel"));
  }

  // =========================
  // UPDATE OFFER - PATCH VALID
  // =========================

  @Test
  void updateOffer_shouldRedirectToDetails_whenValid() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(true);

    mockMvc.perform(patch("/offers/update/{id}", id)
            .param("description", "Very good car description")
            .param("price", "1000")
            .param("model", "M3")
            .param("brand", "BMW")
            .param("condition", "Used")
            .param("engine", "Gasoline")
            .param("mileage", "100000")
            .param("transmission", "Manual")
            .param("year", "2020")
            .param("color", "Black")
            .param("country", "Bulgaria")
            .param("city", "Sofia")
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/details/" + id));
  }

  @Test
  void updateOffer_shouldRedirectToErrorPage_whenInvalid() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(true);

    mockMvc.perform(patch("/offers/update/{id}", id)
            .param("description", "short") // ❌ invalid
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/update/errors/" + id))
            .andExpect(flash().attributeExists("offerUpdateBindingModel"))
            .andExpect(flash().attributeExists(
                    "org.springframework.validation.BindingResult.offerUpdateBindingModel"
            ));
  }

  // =========================
  // DELETE OFFER
  // =========================

  @Test
  void deleteOffer_shouldRedirectHome_whenAuthorized() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(true);

    mockMvc.perform(delete("/offers/{id}", id)
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
  }

  // =========================
  // DELETE OFFER - SOLD
  // =========================

  @Test
  void deleteOffer_shouldMarkAsSold_whenFlagIsTrue() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(true);

    mockMvc.perform(delete("/offers/{id}", id)
            .param("soldOffer", "true")
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection());

    verify(soldOfferService).saveSoldOffer(id);
    verify(offerService).deleteById(id);
  }

  // =========================
  // SECURITY - FORBIDDEN
  // =========================

  @Test
  void updateOffer_shouldReturn403_whenUnauthorized() throws Exception {

    Long id = 1L;

    when(securityService.canModifyOffer(anyString(), eq(id)))
            .thenReturn(false);

    mockMvc.perform(get("/offers/update/{id}", id)
            .with(authentication(createAuth("user1"))))
            .andExpect(status().isForbidden());
  }

  private Authentication createAuth(String username) {

    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setUsername(username);
    user.setPassword("password");
    user.setAccountLocked(false);

    List<GrantedAuthority> authorities =
            List.of(new SimpleGrantedAuthority("ROLE_USER"));

    MobileleUser mobileleUser = new MobileleUser(user, authorities);

    return new UsernamePasswordAuthenticationToken(
            mobileleUser,
            null,
            authorities
    );
  }

  private OfferViewModel createValidOffer() {

    OfferViewModel offer = new OfferViewModel();

    offer.setId(1L);
    offer.setDescription("Test description");
    offer.setPrice(BigDecimal.valueOf(10000));
    offer.setViews(10L);

    offer.setModelBrandName("BMW");
    offer.setModelName("M3");
    offer.setModelYear(2020);
    offer.setModelVehicleType("CAR");

    offer.setMileage(100000.0);

    offer.setCountry(CountryEnum.Bulgaria);
    offer.setCity(CityEnum.Sofia);

    offer.setEngine(EngineEnum.Gasoline);
    offer.setTransmission(TransmissionType.Manual);
    offer.setColor(ColorEnum.Black);
    offer.setCondition(ConditionEnum.Used);

    offer.setCreated(Instant.now());
    offer.setModified(Instant.now());

    offer.setCanModify(false);
    offer.setNotOwnerOrIsAdmin(true);
    offer.setReserved(false);

    UserViewModel seller = new UserViewModel();
    seller.setFirstName("John");
    seller.setLastName("Doe");
    seller.setEmail("john@test.com");
    seller.setPhoneNumber("123456789");
    offer.setSeller(seller);

    PictureServiceModel picture = new PictureServiceModel();
    picture.setPictureUrl("https://test.com/image.jpg");
    picture.setPicturePublicId("public-id");

    offer.setPictures(List.of(picture));

    return offer;
  }
}