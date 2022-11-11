package com.adapters.outbound.persistence.measurement;

import com.domain.model.measurement.Measurement;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "measurementEntity")
@NoArgsConstructor
public class MeasurementDocument {

  private String farmId;
  private Float measurement;
  private Map<String, Float> measurements;
  private Date date;
  @Id
  private ObjectId id;

  @PersistenceCreator
  public MeasurementDocument(String farmId, Float measurement, Map<String, Float> measurements, Date date) {
    this.farmId = farmId;
    this.measurement = measurement;
    this.measurements = measurements;
    this.date = date;
  }

  public static MeasurementDocument fromDomain(Measurement measurement) {
    MeasurementDocumentBuilder builder = builder()
        .date(measurement.getDate())
        .measurement(measurement.getMeasurement())
        .measurements(measurement.getMeasurements())
        .farmId(measurement.getFarmId());
    if (measurement.getId() != null) {
      builder.id(new ObjectId(measurement.getId()));
    }
    return builder.build();
  }

  public static Measurement toDomain(MeasurementDocument measurement) {
    if (measurement == null) {
      return null;
    }
    return Measurement.builder()
        .date(measurement.getDate())
        .measurement(measurement.getMeasurement())
        .id(measurement.getId().toString())
        .measurements(measurement.getMeasurements())
        .farmId(measurement.getFarmId())
        .build();
  }
}
