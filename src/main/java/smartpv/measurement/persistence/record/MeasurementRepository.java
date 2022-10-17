package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;

public interface MeasurementRepository {

  void save(MeasurementEntity measurementEntity);

  List<MeasurementEntity> findAll();
  Optional<MeasurementEntity> findTopByDateAndIdIsNot(String id);

  Optional<MeasurementEntity> findTopByFarmIdAndDateAfter(String farmId, Date after);
}
