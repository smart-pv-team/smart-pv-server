package measuring;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class MeasurementResponse {

  private final String deviceId;
  private final Float measurement;
  private final Date date;


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
