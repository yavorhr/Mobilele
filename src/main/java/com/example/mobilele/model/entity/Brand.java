package com.example.mobilele.model.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
  private String name;
  private Instant created;
  private Instant modified;

  public Brand() {
  }

  @Column(unique = true, nullable = false)
  public String getName() {
    return name;
  }

  @Column(columnDefinition = "DATETIME")
  public Instant getCreated() {
    return created;
  }

  @Column(columnDefinition = "DATETIME")
  public Instant getModified() {
    return modified;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public void setModified(Instant modified) {
    this.modified = modified;
  }

  @PostConstruct
  private void setCreatedTime() {
    setCreated(Instant.now());
  }
}
