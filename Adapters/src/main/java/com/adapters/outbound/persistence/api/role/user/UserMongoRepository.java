package com.adapters.outbound.persistence.api.role.user;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {

  List<UserDocument> findAllByAdminId(String adminId);
}
