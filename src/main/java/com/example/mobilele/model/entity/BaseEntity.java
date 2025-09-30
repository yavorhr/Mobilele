package com.example.mobilele.model.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
  private Long id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
