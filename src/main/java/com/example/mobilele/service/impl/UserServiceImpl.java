package com.example.mobilele.service.impl;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import com.example.mobilele.web.exception.DuplicatePhoneException;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final OfferRepository offerRepository;
  private final UserRoleService roleService;
  private final MobileleUserServiceImpl mobileleUserService;
  private final ModelMapper modelMapper;
  private final SoldOfferRepository soldOfferRepository;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, OfferRepository offerRepository, UserRoleService roleService, MobileleUserServiceImpl mobileleUserService, ModelMapper modelMapper, SoldOfferRepository soldOfferRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.offerRepository = offerRepository;
    this.roleService = roleService;
    this.mobileleUserService = mobileleUserService;
    this.modelMapper = modelMapper;
    this.soldOfferRepository = soldOfferRepository;
  }

  @Override
  public void registerAndLoginUser(UserRegisterServiceModel serviceModel) {
    UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

    UserEntity userEntity = this.modelMapper.map(serviceModel, UserEntity.class);

    userEntity.setPassword(passwordEncoder.encode(serviceModel.getPassword()))
            .setRoles(List.of(userRoleEntity));
    userEntity = userRepository.save(userEntity);

    UserDetails principal = mobileleUserService.loadUserByUsername(userEntity.getUsername());

    Authentication authentication = new UsernamePasswordAuthenticationToken(
            principal,
            userEntity.getPassword(),
            principal.getAuthorities()
    );

    SecurityContextHolder.
            getContext().
            setAuthentication(authentication);
  }

  @Override
  public UserEntity findByUsername(String username) {
    return this.getByUsernameOrThrow(username);
  }

  @Override
  public UserEntity findById(Long id) {
    return this.getByIdOrThrow(id);
  }

  @Override
  public void updateUser(UserEntity user) {
    this.userRepository.save(user);
  }

  @Override
  public boolean isUserNameAvailable(String username) {
    return userRepository.findByUsernameIgnoreCase(username).isEmpty();
  }

  @Override
  public List<UserEntity> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public UserViewModel findUserViewModelById(Long id) {
    UserEntity user = getByIdOrThrow(id);

    return modelMapper.map(user, UserViewModel.class);
  }

  @Override
  public UserViewModel updateUserProfile(Long userId, UserEditBindingModel bindingModel) {
    UserEntity userEntity = this.getByIdOrThrow(userId);

    Optional<UserEntity> existing = userRepository.findByPhoneNumberIgnoreCase(bindingModel.getPhoneNumber());

    if (existing.isPresent() && !existing.get().getId().equals(userId)) {
      throw new DuplicatePhoneException("Phone number already in use");
    }

    userEntity.setFirstName(bindingModel.getFirstName());
    userEntity.setLastName(bindingModel.getLastName());
    userEntity.setPhoneNumber(bindingModel.getPhoneNumber());

    userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserViewModel.class);
  }

  @Transactional
  @Override
  public void deleteProfileById(Long userId) {
    UserEntity userEntity = this.getByIdOrThrow(userId);

    soldOfferRepository.clearSeller(userId);

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);

    this.userRepository.deleteById(userId);
  }

  @Override
  public boolean isEmailAvailable(String email) {
    return userRepository.findByEmailIgnoreCase(email).isEmpty();
  }

  @Override
  public boolean isPhoneNumberAvailable(String phoneNumber) {
    return userRepository.findByPhoneNumberIgnoreCase(phoneNumber).isEmpty();
  }

  @Override
  @Transactional
  public boolean toggleFavorite(String username, Long offerId) {
    boolean exists = userRepository.existsByUsernameAndFavorites_Id(username, offerId);

    UserEntity user = this.getByUsernameOrThrow(username);

    OfferEntity offer = offerRepository
            .findById(offerId)
            .orElseThrow(() -> new RuntimeException("Offer not found"));

    if (exists) {
      user.getFavorites().remove(offer);
      offer.getFavoritedBy().remove(user);
      return false;
    } else {
      user.getFavorites().add(offer);
      offer.getFavoritedBy().add(user);
      return true;
    }
  }

  @Override
  public boolean isOwnerOrIsAdmin(String username, Long offerId) {
    return isOwner(username, offerId) || isAdmin(username);
  }

  @Override
  public boolean isNotOwnerOrIsAdmin(String username, Long offerId) {
    return !isOwner(username, offerId) || isAdmin(username);
  }

  protected boolean isAdmin(String username) {
    return userRepository
            .findByUsername(username)
            .map(user -> user.getRoles().stream()
                    .anyMatch(r -> r.getRole() == UserRoleEnum.ADMIN))
            .orElse(false);
  }

  @Transactional
  public boolean isOwner(String username, Long offerId) {
    return offerRepository
            .findById(offerId)
            .stream()
            .anyMatch(offer -> offer.getSeller().getUsername().equals(username));
  }

  // Helpers
  private UserEntity getByUsernameOrThrow(String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with username: " + username + " not found"));
  }

  private UserEntity getByIdOrThrow(Long userId) {
    return userRepository.findById(userId)
            .orElseThrow(() ->
                    new ObjectNotFoundException("User with id: " + userId + " does not exist!"));
  }

  // Init users
  @Override
  public void seedUsers() {
    if (userRepository.count() > 0) {
      return;
    }

    UserRoleEntity adminRole = this.roleService.findUserRole(UserRoleEnum.ADMIN);
    UserRoleEntity userRole = this.roleService.findUserRole(UserRoleEnum.USER);

    List<UserEntity> users = List.of(
            new UserEntity("admin", "john.atanasoff@gmail.com", "+359888333222",
                    "John", "Atanasoff", passwordEncoder.encode("test"), true,
                    List.of(adminRole, userRole)),

            new UserEntity("user", "peter.ivanov@yahoo.com", "+359888333111",
                    "Petar", "Ivanov", passwordEncoder.encode("test"), true,
                    List.of(userRole)),
            new UserEntity("georgie", "george.mihaylov@yahoo.com", "+359888333333",
                    "George", "Mihaylov", passwordEncoder.encode("test"), true,
                    List.of(userRole))
            , new UserEntity("rado_763", "radoslav.penkov@abv.com", "+359888847621",
                    "Radoslav", "Penkov", passwordEncoder.encode("test"), true,
                    List.of(userRole))
            , new UserEntity("silvia_", "silvia.dimitrova@gmail.com", "+359888987459",
                    "Silvia", "Dimitrova", passwordEncoder.encode("test"), true,
                    List.of(userRole))
            , new UserEntity("yan_peng", "yan.peng@abv.bg", "+359888956105",
                    "Yan", "Peng", passwordEncoder.encode("test"), true,
                    List.of(userRole))
            , new UserEntity("spas40", "spas.kolev@yahoo.com", "+359888873950",
                    "Spas", "Kolev", passwordEncoder.encode("test"), true,
                    List.of(userRole))
            , new UserEntity("radostinka", "radostin.teneff@gmail.com", "+359888859026",
                    "Radostina", "Tenev", passwordEncoder.encode("test"), true,
                    List.of(userRole)),
            new UserEntity("ralka", "ralitsa.uvakova@abv.bg", "+359888830026",
                    "Ralitsa", "Uvakova", passwordEncoder.encode("test"), true,
                    List.of(userRole)),
            new UserEntity("dean_XXL", "dean.deyanov@abv.bg", "+359888683031",
                    "Dean", "Deyanov", passwordEncoder.encode("test"), true,
                    List.of(userRole)),
            new UserEntity("richkata", "richard.emoroigbe@yahoo.com", "+359883985205",
                    "Richard", "Emoro", passwordEncoder.encode("test"), true,
                    List.of(userRole))
    );

    this.userRepository.saveAll(users);
  }
}
