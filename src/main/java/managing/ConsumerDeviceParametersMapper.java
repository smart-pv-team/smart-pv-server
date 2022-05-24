package managing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerDeviceParametersMapper {
    public int priority;
    public boolean onStatus;
    public int powerConsumption;
    public int minHysteresis;
    public int maxHysteresis;

    public ConsumerDeviceParametersMapper(@JsonProperty("priority") Integer priority,
                                          @JsonProperty("onStatus") Boolean onStatus,
                                          @JsonProperty("powerConsumption") Integer powerConsumption,
                                          @JsonProperty("minHysteresis") Integer minHysteresis,
                                          @JsonProperty("maxHysteresis") Integer maxHysteresis) {
        this.priority = priority;
        this.onStatus = onStatus;
        this.powerConsumption = powerConsumption;
        this.minHysteresis = minHysteresis;
        this.maxHysteresis = maxHysteresis;
    }

    public static ConsumerDeviceParametersMapper ofConsumerDevice(ConsumerDeviceEntity consumerDeviceEntity) {
        return new ConsumerDeviceParametersMapper(consumerDeviceEntity.getPriority(),
                consumerDeviceEntity.isOn(),
                consumerDeviceEntity.getPowerConsumption(),
                consumerDeviceEntity.getMinHysteresis(),
                consumerDeviceEntity.getMaxHysteresis());
    }
}
