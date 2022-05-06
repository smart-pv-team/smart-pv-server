package measuring;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;


public class MeasurementEntity {
    @Id
    private String id;
    private String deviceId;
    private Float measurement;
    private Date date;

    public MeasurementEntity(String deviceId, Float measurement, Date date) {
        this.deviceId = deviceId;
        this.measurement = measurement;
        this.date = date;
    }
}
