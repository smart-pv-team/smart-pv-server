package measurement.persistence.record;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementMongoRepository extends
    MongoRepository<MeasurementEntity, String> {

  List<MeasurementEntity> findAllByIdAndDateBetween(String id, Date from, Date to);

  List<MeasurementEntity> findAllByFarmId(String farmId);

  Optional<MeasurementEntity> findTopByFarmIdAndDateAfter(String farmId, Date from);
}
