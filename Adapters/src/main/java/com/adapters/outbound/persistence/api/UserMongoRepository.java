package com.adapters.outbound.persistence.api;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

  List<UserDocument> findByFarmIdIn(List<String> farmIds);
}
