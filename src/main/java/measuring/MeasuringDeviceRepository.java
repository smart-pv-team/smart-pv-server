package measuring;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasuringDeviceRepository extends MongoRepository<MeasuringDeviceEntity, String> {

}
