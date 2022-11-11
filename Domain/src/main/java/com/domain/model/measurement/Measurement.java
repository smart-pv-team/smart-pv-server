package com.domain.model.measurement;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Measurement {

  private String farmId;
  private Float measurement;
  private Map<String, Float> measurements;
  private Date date;
  private String id;

  public Measurement(String farmId, Float measurement, Map<String, Float> measurements, Date date) {
    this.farmId = farmId;
    this.measurement = measurement;
    this.measurements = measurements;
    this.date = date;
  }

  public Measurement withMeasurement(Float measurement) {
    return new Measurement(farmId, measurement, measurements, date, id);
  }
}
