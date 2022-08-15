package consumption;

import com.fasterxml.jackson.annotation.JsonProperty;
import consumption.persistence.ConsumerDeviceEntity;

public class ConsumerDeviceStatusMapper {

  public boolean isOn;

  public ConsumerDeviceStatusMapper(@JsonProperty("isOn") boolean isOn) {
    this.isOn = isOn;
  }

  public static ConsumerDeviceStatusMapper ofConsumerDeviceEntity(
      ConsumerDeviceEntity consumerDeviceEntity) {
    return new ConsumerDeviceStatusMapper(consumerDeviceEntity.isOn());
  }
}
