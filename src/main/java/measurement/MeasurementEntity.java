package measurement;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MeasurementEntity {

  @Id
  private String id;
  private final String deviceId;
  private final Float measurement;
  private final Date date;


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
