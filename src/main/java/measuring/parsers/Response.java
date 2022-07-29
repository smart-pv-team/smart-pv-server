package measuring.parsers;

import java.io.Serializable;
import measuring.MeasurementEntity;

public interface Response extends Serializable {
  MeasurementEntity toMeasurementEntity(String id);
}
