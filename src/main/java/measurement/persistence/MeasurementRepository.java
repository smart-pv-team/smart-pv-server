package measurement.persistence;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementRepository extends MongoRepository<MeasurementEntity, String> {

  List<MeasurementEntity> getAllByDeviceId(String id);
}
