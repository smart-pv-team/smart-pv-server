package management.farm;

import management.farm.persistance.FarmEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FarmRepository extends MongoRepository<FarmEntity, String> {

}
