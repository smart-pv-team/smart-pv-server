package measuring;

import org.springframework.data.annotation.Id;

public class MeasuringDeviceEntity {
    @Id
    private String id;
    private String ipAddress;
    private String dataEndpoint;

    public MeasuringDeviceEntity(String ipAddress, String dataEndpoint){
        this.ipAddress = ipAddress;
        this.dataEndpoint = dataEndpoint;
    }
    public String getIpAddress() {
        return ipAddress;
    }

    public String getDataEndpoint() {
        return dataEndpoint;
    }
}
