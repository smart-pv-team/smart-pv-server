package measurement.persistence.record;

import java.util.Date;
import java.util.List;

public interface MeasurementRepository {

  void save(MeasurementEntity measurementEntity);

  List<MeasurementEntity> getFromTimePeriod(String farmId, Date from,
      Date to);

  List<MeasurementEntity> getAll();

  List<MeasurementEntity> getAllByFarmId(String farmId);
}
