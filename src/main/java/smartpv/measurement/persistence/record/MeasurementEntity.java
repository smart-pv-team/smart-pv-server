package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document()
public class MeasurementEntity {

  private final String farmId;
  private final Float measurement;
  private final Map<String, Float> measurements;
  private final Date date;
  @Id
  private String id;

  @PersistenceCreator
  public MeasurementEntity(String farmId, Float measurement, Map<String, Float> measurements, Date date) {
    this.farmId = farmId;
    this.measurement = measurement;
    this.measurements = measurements;
    this.date = date;
  }

  public MeasurementEntity withMeasurement(Float measurement) {
    return new MeasurementEntity(farmId, measurement, measurements, date, id);
  }
}
