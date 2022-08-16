package consumption.persistence;

import consumption.ControlParameters;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import server.utils.HttpEndpointData;

@Data
public class ConsumerDeviceEntity {

  @Id
  private String id;
  private String farmId;
  private String name;
  private String ipAddress;
  private List<HttpEndpointData> endpoints;
  private Boolean isOn;
  private ControlParameters controlParameters;

  public ConsumerDeviceEntity(String id, String farmId, String name, String ipAddress, Boolean isOn,
      List<HttpEndpointData> endpoints, ControlParameters controlParameters) {
    this.id = id;
    this.farmId = farmId;
    this.name = name;
    this.ipAddress = ipAddress;
    this.isOn = isOn;
    this.endpoints = endpoints;
    this.controlParameters = controlParameters;
  }

}

