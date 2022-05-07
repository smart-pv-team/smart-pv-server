package measuring;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class MeasurementResponse {
    private String deviceId;
    private Float measurement;
    private Date date;


    public MeasurementResponse(@JsonProperty("deviceId") String deviceId,
                               @JsonProperty("measurement") Float measurement,
                               @JsonProperty("date") Date date) {
        this.deviceId = deviceId;
        this.measurement = measurement;
        this.date = date;
    }

    public MeasurementEntity toMeasurementEntity() {
        return new MeasurementEntity(deviceId, measurement, date);
    }
}
