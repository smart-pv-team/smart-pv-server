package com.domain.model.api.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Admin extends Role {

  private String farmId;

  @Builder
  public Admin(String farmId, String token, String name, String email) {
    super(token, name, email);
    this.farmId = farmId;
  }
}
