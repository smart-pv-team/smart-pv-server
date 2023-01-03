package com.adapters.outbound.persistence.consumption;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumptionMongoRepository extends
    MongoRepository<ConsumptionDocument, String> {

  List<ConsumptionDocument> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to);
}
