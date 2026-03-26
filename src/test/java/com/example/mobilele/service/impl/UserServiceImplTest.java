package com.example.mobilele.service.impl;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import com.example.mobilele.web.exception.DuplicatePhoneException;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private OfferRepository offerRepository;

  @Mock
  private UserRoleService roleService;

  @Mock
  private MobileleUserServiceImpl mobileleUserService;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private SoldOfferRepository soldOfferRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @AfterEach
  void clearSecurity() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void registerAndLoginUser_shouldRegisterAndAuthenticate() {
    UserRegisterServiceModel serviceModel = new UserRegisterServiceModel();
    serviceModel.setUsername("user");
    serviceModel.setPassword("123");

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("user");

    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.USER);

    when(roleService.findUserRole(UserRoleEnum.USER)).thenReturn(role);
    when(modelMapper.map(serviceModel, UserEntity.class)).thenReturn(userEntity);
    when(passwordEncoder.encode("123")).thenReturn("encoded");
    when(userRepository.save(any())).thenReturn(userEntity);

    UserDetails userDetails = mock(UserDetails.class);
    when(mobileleUserService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.getAuthorities()).thenReturn(List.of());

    userService.registerAndLoginUser(serviceModel);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void isUserNameAvailable_shouldReturnTrue_whenNotExists() {
    when(userRepository.findByUsernameIgnoreCase("user"))
            .thenReturn(Optional.empty());

    assertTrue(userService.isUserNameAvailable("user"));
  }

  @Test
  void updateUserProfile_shouldUpdateSuccessfully() {
    UserEntity user = new UserEntity();
    user.setId(1L);

    UserEditBindingModel model = new UserEditBindingModel();
    model.setFirstName("John");
    model.setLastName("Doe");
    model.setPhoneNumber("123");

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findByPhoneNumberIgnoreCase("123"))
            .thenReturn(Optional.empty());

    when(modelMapper.map(any(), eq(UserViewModel.class)))
            .thenReturn(new UserViewModel());

    userService.updateUserProfile(1L, model);

    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());

    verify(userRepository).save(user);
  }

  @Test
  void updateUserProfile_shouldThrow_whenPhoneExists() {
    UserEntity user = new UserEntity();
    user.setId(1L);

    UserEntity existing = new UserEntity();
    existing.setId(2L);

    UserEditBindingModel model = new UserEditBindingModel();
    model.setPhoneNumber("123");

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(userRepository.findByPhoneNumberIgnoreCase("123"))
            .thenReturn(Optional.of(existing));

    assertThrows(DuplicatePhoneException.class,
            () -> userService.updateUserProfile(1L, model));
  }

  @Test
  void deleteProfileById_shouldClearRolesAndDelete() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setRoles(new ArrayList<>(List.of(new UserRoleEntity())));

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    userService.deleteProfileById(1L);

    assertTrue(user.getRoles().isEmpty());
    verify(soldOfferRepository).clearSeller(1L);
    verify(userRepository).save(user);
    verify(userRepository).deleteById(1L);
  }

  @Test
  void isOwnerOrIsAdmin_shouldReturnTrue_whenOwner() {
    OfferEntity offer = new OfferEntity();
    UserEntity seller = new UserEntity();
    seller.setUsername("user");
    offer.setSeller(seller);

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    assertTrue(userService.isOwnerOrIsAdmin("user", 1L));
  }

  @Test
  void isAdmin_shouldReturnTrue_whenUserIsAdmin() {
    UserRoleEntity role = new UserRoleEntity();
    role.setRole(UserRoleEnum.ADMIN);

    UserEntity user = new UserEntity();
    user.setRoles(List.of(role));

    when(userRepository.findByUsername("admin"))
            .thenReturn(Optional.of(user));

    assertTrue(userService.isOwnerOrIsAdmin("admin", 999L)); // not owner, but admin
  }

  @Test
  void getUserByUsernameOrThrow_shouldThrow_whenNotFound() {
    when(userRepository.findByUsername("missing"))
            .thenReturn(Optional.empty());

    assertThrows(ObjectNotFoundException.class,
            () -> userService.getUserByUsernameOrThrow("missing"));
  }

  @Test
  void isEmailAvailable_shouldReturnTrue_whenEmailNotExists() {
    when(userRepository.findByEmailIgnoreCase("test@mail.com"))
            .thenReturn(Optional.empty());

    boolean result = userService.isEmailAvailable("test@mail.com");

    assertTrue(result);
  }

  @Test
  void isNotOwnerOrIsAdmin_shouldReturnFalse_whenOwnerAndNotAdmin() {
    OfferEntity offer = new OfferEntity();
    UserEntity seller = new UserEntity();
    seller.setUsername("user");
    offer.setSeller(seller);

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
    when(userRepository.findByUsername("user"))
            .thenReturn(Optional.of(new UserEntity()));

    boolean result = userService.isNotOwnerOrIsAdmin("user", 1L);

    assertFalse(result);
  }

  @Test
  void isNotOwnerOrIsAdmin_shouldReturnTrue_whenAdmin_evenIfOwner() {
    OfferEntity offer = new OfferEntity();
    UserEntity seller = new UserEntity();
    seller.setUsername("admin"); // 👈 same user → isOwner = true
    offer.setSeller(seller);

    UserRoleEntity adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    UserEntity admin = new UserEntity();
    admin.setRoles(List.of(adminRole));

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
    when(userRepository.findByUsername("admin"))
            .thenReturn(Optional.of(admin));

    boolean result = userService.isNotOwnerOrIsAdmin("admin", 1L);

    assertTrue(result);
  }
  @Test
  void isNotOwnerOrIsAdmin_shouldReturnTrue_whenNotOwner() {
    OfferEntity offer = new OfferEntity();
    UserEntity seller = new UserEntity();
    seller.setUsername("other");
    offer.setSeller(seller);

    when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

    boolean result = userService.isNotOwnerOrIsAdmin("user", 1L);

    assertTrue(result);
  }

  @Test
  void isEmailAvailable_shouldReturnFalse_whenEmailExists() {
    when(userRepository.findByEmailIgnoreCase("test@mail.com"))
            .thenReturn(Optional.of(new UserEntity()));

    boolean result = userService.isEmailAvailable("test@mail.com");

    assertFalse(result);
  }

  @Test
  void seedUsers_shouldSeed_whenEmpty() {
    when(userRepository.count()).thenReturn(0L);

    UserRoleEntity admin = new UserRoleEntity();
    admin.setRole(UserRoleEnum.ADMIN);

    UserRoleEntity userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    when(roleService.findUserRole(UserRoleEnum.ADMIN)).thenReturn(admin);
    when(roleService.findUserRole(UserRoleEnum.USER)).thenReturn(userRole);

    when(passwordEncoder.encode(any())).thenReturn("encoded");

    userService.seedUsers();

    verify(userRepository).saveAll(anyList());
  }
}
