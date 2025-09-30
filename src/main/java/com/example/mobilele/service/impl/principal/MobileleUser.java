package com.example.mobilele.service.impl.principal;

import com.example.mobilele.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.List;

public class MobileleUser extends User {
  private Long id;
  private final UserEntity userEntity;

  public MobileleUser(UserEntity userEntity, List<GrantedAuthority> grantedAuthorities) {
    super(
            userEntity.getUsername(),
            userEntity.getPassword(),
            true,
            true,
            true,
            !userEntity.isAccountLocked(),
            grantedAuthorities);

    this.id = userEntity.getId();
    this.userEntity = userEntity;
  }

  public String getUsername() {
    return userEntity.getUsername();
  }

  public Long getId() {
    return id;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }
}
