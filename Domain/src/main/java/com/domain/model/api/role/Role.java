package com.domain.model.api.role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {

  private String token;
  private String name;
  private String email;
}
