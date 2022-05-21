package managing;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumerDeviceRepository extends MongoRepository<ConsumerDeviceEntity, String> {

}
