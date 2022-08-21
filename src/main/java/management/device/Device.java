package management.device;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import server.utils.HttpEndpointData;

@Data
public class Device {

  @Id
  private String id;
  private String farmId;
  private String name;
  private String ipAddress;
  private List<HttpEndpointData> endpoints;
}
