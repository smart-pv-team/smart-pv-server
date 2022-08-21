package consumption;

import com.fasterxml.jackson.annotation.JsonProperty;
import consumption.persistence.device.ConsumptionDeviceEntity;

// TODO: Just example
public class ConsumptionMapper {

  public boolean isOn;

  public ConsumptionMapper(@JsonProperty("isOn") boolean isOn) {
    this.isOn = isOn;
  }

  public static ConsumptionMapper ofConsumerDeviceEntity(
      ConsumptionDeviceEntity consumptionDeviceEntity) {
    return new ConsumptionMapper(consumptionDeviceEntity.getIsOn());
  }
}
