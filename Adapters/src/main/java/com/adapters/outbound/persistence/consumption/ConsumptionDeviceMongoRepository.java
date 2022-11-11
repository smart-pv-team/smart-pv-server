package com.adapters.outbound.persistence.consumption;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumptionDeviceMongoRepository extends
    MongoRepository<ConsumptionDeviceDocument, String> {

  List<ConsumptionDeviceDocument> findAllByFarmId(String farmId);

  Optional<ConsumptionDeviceDocument> findById(String id);

  List<ConsumptionDeviceDocument> findAllByFarmIdAndIdIsIn(String farmId, List<String> ids);
}
