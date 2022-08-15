package consumption;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumerDeviceMongoRepository extends
    MongoRepository<ConsumerDeviceEntity, String> {

  List<ConsumerDeviceEntity> findAllByFarmId(String farmId);
}
