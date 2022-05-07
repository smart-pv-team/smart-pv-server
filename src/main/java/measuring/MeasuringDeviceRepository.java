package measuring;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MeasuringDeviceRepository extends MongoRepository<MeasuringDeviceEntity, String> {
}
