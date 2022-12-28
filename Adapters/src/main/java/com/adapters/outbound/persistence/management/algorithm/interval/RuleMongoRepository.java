package com.adapters.outbound.persistence.management.algorithm.interval;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleMongoRepository extends MongoRepository<RuleDocument, String> {

  List<RuleDocument> getAllByIntervalId(String intervalId);
}
