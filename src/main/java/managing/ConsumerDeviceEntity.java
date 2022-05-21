package managing;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class ConsumerDeviceEntity {
    @Id
    private String id;
    private String ipAddress;
    private String dataEndpoint;
    private int priority;
    private boolean onStatus;
    private int powerConsumption;
    private int minHysteresis;
    private int maxHysteresis;

    public ConsumerDeviceEntity(String ipAddress, String dataEndpoint) {
        this.ipAddress = ipAddress;
        this.dataEndpoint = dataEndpoint;
    }

    @PersistenceConstructor
    public ConsumerDeviceEntity(String ipAddress, String dataEndpoint, int priority, boolean onStatus, int powerConsumption, int minHysteresis, int maxHysteresis) {
        this.ipAddress = ipAddress;
        this.dataEndpoint = dataEndpoint;
        this.priority = priority;
        this.onStatus = onStatus;
        this.powerConsumption = powerConsumption;
        this.minHysteresis = minHysteresis;
        this.maxHysteresis = maxHysteresis;
    }

    public String getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDataEndpoint() {
        return dataEndpoint;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getOnStatus() {
        return onStatus;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public int getMinHysteresis() {
        return minHysteresis;
    }

    public int getMaxHysteresis() {
        return maxHysteresis;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setOnStatus(boolean onStatus) {
        this.onStatus = onStatus;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public void setMinHysteresis(int minHysteresis) {
        this.minHysteresis = minHysteresis;
    }

    public void setMaxHysteresis(int maxHysteresis) {
        this.maxHysteresis = maxHysteresis;
    }
}

