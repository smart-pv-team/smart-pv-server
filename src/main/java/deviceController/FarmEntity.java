package deviceController;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class FarmEntity {
  @Id
  private String Id;
  private final String name;
  private final String description;

}
