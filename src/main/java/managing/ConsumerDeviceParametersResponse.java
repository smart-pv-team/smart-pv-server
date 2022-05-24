package managing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerDeviceParametersResponse {
    public int priority;
    public boolean onStatus;
    public int powerConsumption;
    public int minHysteresis;
    public int maxHysteresis;

    public ConsumerDeviceParametersResponse(@JsonProperty("priority") Integer priority,
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
}
