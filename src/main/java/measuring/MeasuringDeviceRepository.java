package measuring;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasuringDeviceRepository extends MongoRepository<MeasuringDeviceEntity, String> {
  MeasuringDeviceEntity getFirstById(String id);
}
