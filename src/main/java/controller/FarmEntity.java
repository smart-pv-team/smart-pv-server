package controller;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class FarmEntity {

  @Id
  private final String id;
  private final String name;
  private final String description;

}
