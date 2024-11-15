package com.example.mobilele.service.impl;

import com.example.mobilele.model.entity.UserEntity;
import com.example.mobilele.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileleUserServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public MobileleUserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity =
            this.userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with name not found"));

    return mapToUserDetails(userEntity);
  }

  private static UserDetails mapToUserDetails(UserEntity userEntity) {
    List<GrantedAuthority> authorities = userEntity
            .getRoles()
            .stream()
            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).collect(Collectors.toList());

    return new User(
            userEntity.getUsername(),
            userEntity.getPassword(),
            authorities);
  }
}
