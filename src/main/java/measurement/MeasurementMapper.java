package measurement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import measurement.persistence.record.MeasurementEntity;
import server.utils.Dates;

public class MeasurementMapper {

  public Float measurement;
  public LocalDateTime date;

  public MeasurementMapper(
      @JsonProperty("measurement") Float measurement,
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      @JsonProperty("date") LocalDateTime date
  ) {
    this.measurement = measurement;
    this.date = date;
  }

  public static MeasurementMapper ofMeasurementSumEntity(MeasurementEntity measurementEntity) {
    return new MeasurementMapper(measurementEntity.getMeasurement(),
        Dates.dateToLocalDateTime(measurementEntity.getDate()));
  }


}