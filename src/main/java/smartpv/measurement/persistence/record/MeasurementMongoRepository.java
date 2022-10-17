package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementMongoRepository extends
    MongoRepository<MeasurementEntity, String> {

  List<MeasurementEntity> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to);
  Optional<MeasurementEntity> findTopByDateAndIdIsNot(String id);
}
