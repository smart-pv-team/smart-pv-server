package com.adapters.outbound.persistence.management.algorithm.interval;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervalMongoRepository extends MongoRepository<IntervalDocument, String> {

  List<IntervalDocument> findAllByFarmId(String farmId);
}
