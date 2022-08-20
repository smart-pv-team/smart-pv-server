package consumption.persistence.status;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumerDeviceStatusMongoRepository extends
    MongoRepository<ConsumerDeviceStatusEntity, String> {

}
