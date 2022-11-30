package com.adapters.outbound.persistence.api.role;


import lombok.AllArgsConstructor;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@AllArgsConstructor
public class RoleEntity {

  private String name;
  private String email;
  @Id
  private String id;

}