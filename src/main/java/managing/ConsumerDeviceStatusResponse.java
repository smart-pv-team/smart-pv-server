package managing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerDeviceStatusResponse {

    public boolean onStatus;

    public ConsumerDeviceStatusResponse(@JsonProperty("onStatus") boolean onStatus) {
        this.onStatus = onStatus;
    }
}
