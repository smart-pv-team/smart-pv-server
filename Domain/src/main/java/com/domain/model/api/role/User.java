package com.domain.model.api.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Role {

  private String adminId;

  @Builder
  public User(String token, String name, String email, String adminId) {
    super(token, name, email);
    this.adminId = adminId;
  }
}