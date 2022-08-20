package measurement.persistence.record;

import java.util.List;

public interface MeasurementRepository {

  void save(List<MeasurementEntity> measurementEntities);

}
