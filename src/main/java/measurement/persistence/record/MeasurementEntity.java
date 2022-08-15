package measurement.persistence.record;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MeasurementEntity {

  private final String deviceId;
  private final Float measurement;
  private final Date date;
  @Id
  private String id;


  public MeasurementEntity(String deviceId,
      Float measurement,
      Date date) {
    this.deviceId = deviceId;
    this.measurement = measurement;
    this.date = date;
  }

  public Float getMeasurement() {
    return measurement;
  }
}
