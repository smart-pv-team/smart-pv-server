package managing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerDeviceStatusMapper {

    public boolean isOn;

    public ConsumerDeviceStatusMapper(@JsonProperty("isOn") boolean isOn) {
        this.isOn = isOn;
    }

    public static ConsumerDeviceStatusMapper ofConsumerDeviceEntity(ConsumerDeviceEntity consumerDeviceEntity){
        return new ConsumerDeviceStatusMapper(consumerDeviceEntity.isOn());
    }
}
