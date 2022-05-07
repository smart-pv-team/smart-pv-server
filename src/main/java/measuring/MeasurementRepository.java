package measuring;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MeasurementRepository extends MongoRepository<MeasurementEntity, String> {
    List<MeasurementEntity> getAllByDeviceId(String id);
}
