package measurement.persistence;

import java.util.List;
import org.springframework.data.annotation.Id;
import server.utils.HttpEndpointData;


public record MeasurementDeviceEntity(@Id String id, String farm, String name, String ipAddress,
                                      List<HttpEndpointData> endpoints) {

}
