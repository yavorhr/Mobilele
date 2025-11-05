package com.example.mobilele.service.impl;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.entity.OfferEntity;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.entity.UserRoleEntity;
import com.example.mobilele.model.entity.enums.UserRoleEnum;
import com.example.mobilele.model.view.user.UserViewModel;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserRoleService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.service.impl.principal.MobileleUserServiceImpl;
import com.example.mobilele.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final OfferRepository offerRepository;
  private final UserRoleService roleService;
  private final MobileleUserServiceImpl mobileleUserService;
  private final ModelMapper modelMapper;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, OfferRepository offerRepository, UserRoleService roleService, MobileleUserServiceImpl mobileleUserService, ModelMapper modelMapper) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.offerRepository = offerRepository;
    this.roleService = roleService;
    this.mobileleUserService = mobileleUserService;
    this.modelMapper = modelMapper;
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

  @Override
  public void deleteProfileById(Long userId) {
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
    UserEntity user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    OfferEntity offer = offerRepository
            .findById(offerId)
            .orElseThrow(() -> new RuntimeException("Offer not found"));

    boolean added;

    if (user.getFavorites().contains(offer)) {
      user.getFavorites().remove(offer);
      added = false;
    } else {
      user.getFavorites().add(offer);
      added = true;
    }

    userRepository.save(user);
    return added;
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

  @Override
  public void initUsers() {
    if (userRepository.count() == 0) {
      UserRoleEntity adminRole = this.roleService.findUserRole(UserRoleEnum.ADMIN);
      UserRoleEntity userRoleEntity = this.roleService.findUserRole(UserRoleEnum.USER);

      UserEntity admin = createUser("admin", "john.atanasoff@gmail.com", "+359888333222", "John", "Atanasoff", "test");
      admin.setRoles(List.of(adminRole, userRoleEntity));

      UserEntity userEntity = createUser("user", "peter.ivanov@yahoo.com", "+359888333111", "Petar", "Ivanov", "test");
      userEntity.setRoles(List.of(userRoleEntity));

      this.userRepository.saveAll(List.of(admin, userEntity));
    }
  }

  // Helpers
  private UserEntity createUser(String username, String email, String phoneNumber, String firstName, String lastName, String password) {
    UserEntity userEntity = new UserEntity();

    userEntity
            .setUsername(username)
            .setEmail(email)
            .setPhoneNumber(phoneNumber)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setPassword(passwordEncoder.encode(password));

    return userEntity;
  }
}
