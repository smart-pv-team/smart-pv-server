package measurement.persistence.sum;

import java.util.Date;
import java.util.List;

public interface MeasurementSumRepository {
  void save(List<MeasurementSumEntity> measurementSumEntity);
  List<MeasurementSumEntity> getFromTimePeriod(String farmId, Date from,
      Date to);
  List<MeasurementSumEntity> getAll();
  List<MeasurementSumEntity> getAllByFarmId(String farmId);
}
