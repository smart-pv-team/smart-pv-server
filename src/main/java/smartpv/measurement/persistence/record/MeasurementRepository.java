package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.List;

public interface MeasurementRepository {

  void save(MeasurementEntity measurementEntity);

  List<MeasurementEntity> findAll();

  List<MeasurementEntity> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to);
}
