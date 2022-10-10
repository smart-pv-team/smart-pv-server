package smartpv.consumption.persistence.device;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import smartpv.consumption.ControlParameters;
import smartpv.management.device.Device;
import smartpv.server.utils.HttpEndpointData;

@Getter
@Setter
@Document(collection = "consumptionDeviceEntity")
public class ConsumptionDeviceEntity extends Device {

  private Boolean isOn;
  private ControlParameters controlParameters;
  private Date lastStatusChange;

  public ConsumptionDeviceEntity(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints) {
    super(id, farmId, name, ipAddress, endpoints);
  }
}

