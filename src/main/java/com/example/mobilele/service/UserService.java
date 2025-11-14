package com.example.mobilele.service;

import com.example.mobilele.model.binding.user.UserEditBindingModel;
import com.example.mobilele.model.service.user.UserRegisterServiceModel;
import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.model.view.user.UserAdministrationViewModel;
import com.example.mobilele.model.view.user.UserViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

  void initUsers();

  void registerAndLoginUser(UserRegisterServiceModel serviceModel);

  UserEntity findByUsername(String username);

  UserEntity findById(Long id);

  void updateUser(UserEntity user);

  boolean isUserNameAvailable(String userName);

  void increaseUserFailedLoginAttempts(UserEntity user);

  void lockAccount(UserEntity user);

  void resetFailedAttempts(UserEntity user);

  List<UserEntity> findLockedUsers();

  UserViewModel findUserViewModelById(Long id);

  UserViewModel updateUserProfile(Long userId, UserEditBindingModel bindingModel);

  void deleteProfileById(Long userId);

  boolean isEmailAvailable(String email);

  boolean isPhoneNumberAvailable(String phoneNumber);

  boolean toggleFavorite(String name, Long offerId);

  boolean isOwnerOrIsAdmin(String username, Long routeId);

  boolean isNotOwnerOrIsAdmin(String username, Long routeId);

  boolean isOwner(String username, Long routeId);

  void deleteOfferFromFavorites(Long id);

  Page<UserAdministrationViewModel> searchPaginatedUsersPerEmail(String emailQuery, Pageable pageable);

}
