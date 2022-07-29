package measuring;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import server.utils.HttpEndpointData;


@Data
public class MeasuringDeviceEntity {

  @Id
  private String id;
  private String name;
  private final String ipAddress;
  private final List<HttpEndpointData> endpoints;
}
