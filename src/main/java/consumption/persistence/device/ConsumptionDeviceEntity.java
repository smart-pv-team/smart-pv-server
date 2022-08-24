package consumption.persistence.device;

import consumption.ControlParameters;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import management.device.Device;
import org.springframework.data.mongodb.core.mapping.Document;
import server.utils.HttpEndpointData;

@Getter
@Setter
@Document()
public class ConsumptionDeviceEntity extends Device {

  private Boolean isOn;
  private ControlParameters controlParameters;

  public ConsumptionDeviceEntity(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints) {
    super(id, farmId, name, ipAddress, endpoints);
  }
}

