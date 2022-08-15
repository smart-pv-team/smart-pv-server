package measurement.persistence.record;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementMongoRepository extends MongoRepository<MeasurementEntity, String> {

  List<MeasurementEntity> getAllByDeviceId(String id);
}
