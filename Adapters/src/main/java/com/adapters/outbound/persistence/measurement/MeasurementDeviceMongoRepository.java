package com.adapters.outbound.persistence.measurement;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementDeviceMongoRepository extends
    MongoRepository<MeasurementDeviceDocument, String> {

  List<MeasurementDeviceDocument> findAll();

  List<MeasurementDeviceDocument> findAllByFarmId(String farmId);

  Optional<MeasurementDeviceDocument> getFirstById(String id);
}
