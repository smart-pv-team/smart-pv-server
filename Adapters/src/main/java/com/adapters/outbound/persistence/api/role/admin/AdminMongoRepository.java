package com.adapters.outbound.persistence.api.role.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminMongoRepository extends MongoRepository<AdminDocument, String> {

}
