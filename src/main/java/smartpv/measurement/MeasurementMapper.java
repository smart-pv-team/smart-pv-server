package smartpv.measurement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import smartpv.measurement.persistence.record.MeasurementEntity;
import smartpv.server.utils.DateTimeUtils;

public class MeasurementMapper {

  public Float measurement;
  public LocalDateTime date;

  public MeasurementMapper(
      Float measurement,
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      @JsonProperty("date") LocalDateTime date
  ) {
    this.measurement = measurement;
    this.date = date;
  }

  public static MeasurementMapper ofMeasurementSumEntity(MeasurementEntity measurementEntity) {
    return new MeasurementMapper(measurementEntity.getMeasurement(),
        DateTimeUtils.dateToLocalDateTime(measurementEntity.getDate()));
  }


}
