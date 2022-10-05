package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeasurementRepository {

  void save(MeasurementEntity measurementEntity);

  List<MeasurementEntity> findAll();

  Optional<MeasurementEntity> findTopByFarmIdAndDateAfter(String farmId, Date after);
}
