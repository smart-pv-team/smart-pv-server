package com.adapters.outbound.persistence.measurement;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementMongoRepository extends
    MongoRepository<MeasurementDocument, String> {

  List<MeasurementDocument> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to);
}
