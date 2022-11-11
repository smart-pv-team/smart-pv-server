package com.adapters.inbound.http.measurement;

import com.adapters.outbound.persistence.measurement.MeasurementDocument;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class MeasurementResponseMapper {

  public Float measurement;
  public Date date;

  public MeasurementResponseMapper(
      Float measurement,
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      @JsonProperty("date") Date date
  ) {
    this.measurement = measurement;
    this.date = date;
  }

  public static MeasurementResponseMapper ofMeasurementEntity(MeasurementDocument measurementEntity) {
    return new MeasurementResponseMapper(measurementEntity.getMeasurement(), measurementEntity.getDate());
  }


}
