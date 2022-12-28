package com.adapters.outbound.persistence.management.farm;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmMongoRepository extends MongoRepository<FarmDocument, String> {

  Optional<FarmDocument> getById(String id);
}
