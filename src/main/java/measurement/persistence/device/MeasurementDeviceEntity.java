package measurement.persistence.device;

import java.util.List;
import org.springframework.data.annotation.Id;
import server.utils.HttpEndpointData;


public record MeasurementDeviceEntity(@Id String id, String farmId, String name, String ipAddress,
                                      List<HttpEndpointData> endpoints) {

}
