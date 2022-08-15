package measurement.persistence.sum;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementSumMongoRepository extends
    MongoRepository<MeasurementSumEntity, String> {

  List<MeasurementSumEntity> findAllByIdAndDateBetween(String id, Date from, Date to);

  List<MeasurementSumEntity> findAllByFarmId(String farmId);

  Optional<MeasurementSumEntity> findTopByFarmIdOrderByDateDesc(String farmId);
}
