package measurement.parsers;

import java.io.Serializable;
import measurement.persistence.MeasurementEntity;

public interface Response extends Serializable {
  MeasurementEntity toMeasurementEntity(String id);
}
