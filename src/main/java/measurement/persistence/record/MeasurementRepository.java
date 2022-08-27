package measurement.persistence.record;

import java.util.Date;
import java.util.Optional;

public interface MeasurementRepository {

  void save(MeasurementEntity measurementEntity);

  Optional<MeasurementEntity> findTopByFarmIdAndDateAfter(String farmId, Date after);
}
