package consumption;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Data
public class ConsumerDeviceEntity {

  @Id
  private String id;
  private String farmId;
  private String ipAddress;
  private String dataEndpoint;
  private int priority;
  private boolean isOn;
  private float powerConsumption;
  private int minHysteresis;
  private int maxHysteresis;
  private boolean isLocked;

  public ConsumerDeviceEntity(String ipAddress, String dataEndpoint, String farmId) {
    this.ipAddress = ipAddress;
    this.dataEndpoint = dataEndpoint;
    this.farmId = farmId;
  }

  @PersistenceConstructor
  public ConsumerDeviceEntity(String ipAddress, String dataEndpoint, int priority, boolean isOn,
      float powerConsumption, int minHysteresis, int maxHysteresis, boolean isLocked, String farmId) {
    this.ipAddress = ipAddress;
    this.farmId = farmId;
    this.dataEndpoint = dataEndpoint;
    this.priority = priority;
    this.isOn = isOn;
    this.powerConsumption = powerConsumption;
    this.minHysteresis = minHysteresis;
    this.maxHysteresis = maxHysteresis;
    this.isLocked = isLocked;
  }

  public ConsumerDeviceEntity withParameters(
      ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
    return new ConsumerDeviceEntity(
        this.ipAddress,
        this.dataEndpoint,
        consumerDeviceParametersMapper.priority,
        consumerDeviceParametersMapper.isOn,
        consumerDeviceParametersMapper.powerConsumption,
        consumerDeviceParametersMapper.minHysteresis,
        consumerDeviceParametersMapper.maxHysteresis,
        this.isLocked,
        this.farmId
    );
  }

  public ConsumerDeviceEntity withId(String id) {
    this.id = id;
    return this;
  }

}

