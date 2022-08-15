package consumption;

import com.fasterxml.jackson.annotation.JsonProperty;
import consumption.persistence.ConsumerDeviceEntity;

public class ConsumerDeviceParametersMapper {

  public int priority;
  public boolean isOn;
  public float powerConsumption;
  public int minHysteresis;
  public int maxHysteresis;

  public ConsumerDeviceParametersMapper(@JsonProperty("priority") Integer priority,
      @JsonProperty("isOn") Boolean isOn,
      @JsonProperty("powerConsumption") Float powerConsumption,
      @JsonProperty("minHysteresis") Integer minHysteresis,
      @JsonProperty("maxHysteresis") Integer maxHysteresis) {
    this.priority = priority;
    this.isOn = isOn;
    this.powerConsumption = powerConsumption;
    this.minHysteresis = minHysteresis;
    this.maxHysteresis = maxHysteresis;
  }

  public static ConsumerDeviceParametersMapper ofConsumerDevice(
      ConsumerDeviceEntity consumerDeviceEntity) {
    return new ConsumerDeviceParametersMapper(consumerDeviceEntity.getPriority(),
        consumerDeviceEntity.isOn(),
        consumerDeviceEntity.getPowerConsumption(),
        consumerDeviceEntity.getMinHysteresis(),
        consumerDeviceEntity.getMaxHysteresis());
  }
}
