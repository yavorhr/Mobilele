package com.example.mobilele.repository;

import com.example.mobilele.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmailIgnoreCase(String email);

  Optional<UserEntity> findByUsernameIgnoreCase(String username);

  Optional<UserEntity> findByPhoneNumberIgnoreCase(String phoneNumber);

  @Query("SELECT u FROM UserEntity u Where u.accountLocked")
  List<UserEntity> findAllLockedUsers();

  List<UserEntity> findAllByEmailContainingIgnoreCase(String email);
}
