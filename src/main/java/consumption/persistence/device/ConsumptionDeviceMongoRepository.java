package consumption.persistence.device;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumptionDeviceMongoRepository extends
    MongoRepository<ConsumptionDeviceEntity, String> {

  List<ConsumptionDeviceEntity> findAllByFarmId(String farmId);
}