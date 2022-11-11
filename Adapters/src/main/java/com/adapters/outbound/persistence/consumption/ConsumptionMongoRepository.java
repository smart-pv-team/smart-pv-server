package com.adapters.outbound.persistence.consumption;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumptionMongoRepository extends
    MongoRepository<ConsumptionDocument, String> {

}
