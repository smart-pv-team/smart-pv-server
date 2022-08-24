package measurement.persistence.device;

import java.util.List;
import management.device.Device;
import org.springframework.data.mongodb.core.mapping.Document;
import server.utils.HttpEndpointData;

@Document()
public class MeasurementDeviceEntity extends Device {

  public MeasurementDeviceEntity(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints) {
    super(id, farmId, name, ipAddress, endpoints);
  }
}
