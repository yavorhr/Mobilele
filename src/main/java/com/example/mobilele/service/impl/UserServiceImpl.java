package com.example.mobilele.service.impl;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.UserUpdateStatusResponse;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.SoldOfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User with username" + username + "does not exist!"));
  }

  @Override
  public UserEntity findById(Long id) {
    return this.userRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("User with id" + id + "does not exist!"));
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
  public void increaseUserFailedLoginAttempts(UserEntity user) {
    int failedAttempts = user.getFailedLoginAttempts() + 1;
    user.setFailedLoginAttempts(failedAttempts);

    if (user.getFailedLoginAttempts() == 3) {
      this.lockAccount(user);
    } else {
      userRepository.save(user);
    }
  }

  @Override
  public List<UserEntity> findLockedUsers() {
    return this.userRepository.findAllLockedUsers();
  }

  @Override
  public List<UserEntity> findAll() {
    return this.userRepository.findAll();
  }

  @Override
  public UserViewModel findUserViewModelById(Long id) {
    return this.userRepository
            .findById(id)
            .map(u -> this.modelMapper.map(u, UserViewModel.class))
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + id + " was not found!"));
  }

  @Override
  public UserViewModel updateUserProfile(Long userId, UserEditBindingModel bindingModel) {
    UserEntity userEntity = this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + userId + " was not found!"));

    userEntity.setFirstName(bindingModel.getFirstName());
    userEntity.setLastName(bindingModel.getLastName());
    userEntity.setPhoneNumber(bindingModel.getPhoneNumber());

    userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserViewModel.class);
  }

  @Transactional
  @Override
  public void deleteProfileById(Long userId) {
    UserEntity userEntity = this.userRepository
            .findById(userId)
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + userId + " does not exist!"));


    soldOfferRepository.clearSeller(userId);

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);

    this.userRepository.deleteById(userId);
  }

  @Override
  public void deleteUser(String username) {
    UserEntity userEntity = this.userRepository
            .findByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + username + " does not exist!"));

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);
    this.userRepository.delete(userEntity);
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
    UserEntity user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    OfferEntity offer = offerRepository
            .findById(offerId)
            .orElseThrow(() -> new RuntimeException("Offer not found"));

    boolean added;

    if (user.getFavorites().contains(offer)) {
      user.getFavorites().remove(offer);
      offer.getFavoritedBy().remove(user);
      added = false;
    } else {
      user.getFavorites().add(offer);
      offer.getFavoritedBy().add(user);
      added = true;
    }

    userRepository.save(user);
    return added;
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

    boolean result = offerRepository
            .findById(offerId)
            .stream()
            .anyMatch(offer -> offer.getSeller().getUsername().equals(username));

    return result;
  }

  @Override
  @Transactional
  public void deleteOfferFromFavorites(Long offerId) {
    List<UserEntity> users = userRepository.findAll();

    for (UserEntity user : users) {
      boolean modified = user.getFavorites()
              .removeIf(o -> o.getId().equals(offerId));

      if (modified) {
        userRepository.save(user);
      }
    }
  }

  @Override
  public UserUpdateStatusResponse changeAccess(String username) {
    UserEntity userEntity =
            this.userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the username " + username + " was not found!"));

    if (userEntity.isEnabled()) {
      userEntity.setEnabled(false);
    } else {
      userEntity.setEnabled(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public UserUpdateStatusResponse modifyLockStatus(String username) {
    UserEntity userEntity =
            this.userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the username " + username + " was not found!"));

    if (userEntity.isAccountLocked()) {
      userEntity.setAccountLocked(false);
    } else {
      userEntity.setAccountLocked(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser) {
    return !loggedInUser.equals(targetUser);

  }

  @Override
  public void updateUserRoles(String username, String[] roles) {
    UserEntity userEntity =
            this.userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the username " + username + " was not found!"));

    List<UserRoleEntity> userRoles = new ArrayList<>();

    Arrays.stream(roles).forEach(r -> {
      UserRoleEntity roleEntity = this.roleService.findUserRole(UserRoleEnum.valueOf(r));
      userRoles.add(roleEntity);
    });

    userEntity.setRoles(userRoles);
    this.userRepository.save(userEntity);
  }

  @Override
  public Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String email, Pageable pageable) {
    List<UserEntity> users = userRepository.findAllByEmailContainingIgnoreCase(email);

    int start = Math.toIntExact(pageable.getOffset());
    int end = Math.min(start + pageable.getPageSize(), users.size());

    List<UserEntity> pagedUsers = users.subList(start, end);

    List<UserAdministrationViewModel> viewModels =
            pagedUsers.stream()
                    .map(u -> {
                      UserAdministrationViewModel vm = modelMapper.map(u, UserAdministrationViewModel.class);

                      Set<UserRoleEnum> roleEnums = u.getRoles().stream()
                              .map(UserRoleEntity::getRole)
                              .collect(Collectors.toSet());

                      vm.setRoles(roleEnums);
                      return vm;
                    })
                    .toList();

    return new PageImpl<>(viewModels, pageable, users.size());
  }

  @Override
  public void lockAccount(UserEntity user) {
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().plusMinutes(15));
    user.setFailedLoginAttempts(0);

    userRepository.save(user);
  }

  @Override
  public void resetFailedAttempts(UserEntity user) {
    user.setFailedLoginAttempts(0);
    this.userRepository.save(user);
  }

  // Init users
  @Override
  public void initUsers() {
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
