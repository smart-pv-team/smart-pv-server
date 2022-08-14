package measurement;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementDeviceRepository extends MongoRepository<MeasurementDeviceEntity, String> {

  MeasurementDeviceEntity getFirstById(String id);
}
