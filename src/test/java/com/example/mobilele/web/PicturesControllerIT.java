package com.example.mobilele.web;

import com.example.mobilele.config.security.SecurityService;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.service.user.PicturesAddServiceModel;
import com.example.mobilele.service.PictureService;
import com.example.mobilele.service.impl.principal.MobileleUser;
import com.example.mobilele.util.cloudinary.CloudinaryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PicturesControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CloudinaryService cloudinaryService;

  @MockBean
  private PictureService pictureService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean(name = "security")
  private SecurityService securityService;

  //=======================
  // ADD PICTURE - SUCCESS
  //=======================

  @Test
  void addOfferPictures_shouldRedirectToDetails_whenAuthorized() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(), eq(offerId)))
            .thenReturn(true);

    when(modelMapper.map(any(), eq(PicturesAddServiceModel.class)))
            .thenReturn(new PicturesAddServiceModel());

    mockMvc.perform(multipart("/pictures")
            .param("offerId", String.valueOf(offerId))
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/offers/details/" + offerId));

    verify(pictureService).addPicturesToOffer(any());
  }

  //=========================
  // ADD PICTURE - FORBIDDEN
  //=========================

  @Test
  void addOfferPictures_shouldReturn403_whenUnauthorized() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(), eq(offerId)))
            .thenReturn(false);

    mockMvc.perform(multipart("/pictures")
            .param("offerId", String.valueOf(offerId))
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().isForbidden());
  }

  @Test
  void deletePicture_shouldReturn204_whenSuccessful() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(eq("user1"), any()))
            .thenReturn(true);

    when(cloudinaryService.delete("public-id"))
            .thenReturn(true);

    mockMvc.perform(delete("/pictures")
            .param("public_id", "public-id")
            .param("offer_id", String.valueOf(offerId))
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andDo(print())
            .andExpect(status().isNoContent());

    verify(pictureService).deleteByPublicId("public-id");
  }

  @Test
  void deletePicture_shouldReturn500_whenCloudinaryFails() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(),  any()))
            .thenReturn(true);

    when(cloudinaryService.delete("public-id"))
            .thenReturn(false);

    mockMvc.perform(delete("/pictures")
            .param("public_id", "public-id")
            .param("offer_id", String.valueOf(offerId))
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andExpect(status().isInternalServerError());
  }

  @Test
  void deletePicture_shouldReturn403_whenUnauthorized() throws Exception {

    Long offerId = 1L;

    when(securityService.canModifyOffer(anyString(), eq(offerId)))
            .thenReturn(false);

    mockMvc.perform(delete("/pictures")
            .param("public_id", "public-id")
            .param("offer_id", String.valueOf(offerId))
            .with(csrf())
            .with(authentication(createAuth("user1"))))
            .andDo(print())
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
}