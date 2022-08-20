package management.farm;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FarmRepository extends MongoRepository<FarmEntity, String> {

}
