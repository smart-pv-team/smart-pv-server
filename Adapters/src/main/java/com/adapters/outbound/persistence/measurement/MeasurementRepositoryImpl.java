package com.adapters.outbound.persistence.measurement;

import static com.adapters.outbound.persistence.Fields.DATE_FIELD;
import static com.adapters.outbound.persistence.Fields.FARM_ID_FIELD;
import static com.adapters.outbound.persistence.Fields.MEASUREMENTS_FIELD;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;

import com.adapters.outbound.persistence.Fields;
import com.adapters.outbound.persistence.MongoUtils;
import com.domain.model.farm.Device;
import com.domain.model.measurement.Measurement;
import com.domain.ports.measurement.MeasurementRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MeasurementRepositoryImpl implements MeasurementRepository {

  private final MeasurementMongoRepository measurementMongoRepository;
  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;
  private final MongoCollection<MeasurementDocument> collection;

  public MeasurementRepositoryImpl(MeasurementMongoRepository measurementMongoRepository,
      MeasurementDeviceMongoRepository measurementDeviceMongoRepository, MongoDatabase mongoDatabase) {
    this.measurementMongoRepository = measurementMongoRepository;
    this.measurementDeviceMongoRepository = measurementDeviceMongoRepository;
    this.collection = MongoUtils.getCollection(mongoDatabase, MeasurementDocument.class);
  }

  @Override
  public List<Measurement> findAll() {
    return measurementMongoRepository.findAll().stream().map(MeasurementDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void save(Measurement measurement) {
    measurementMongoRepository.save(MeasurementDocument.fromDomain(measurement));
  }

  @Override
  public List<Measurement> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to) {
    return measurementMongoRepository.findAllByFarmIdAndDateIsBetween(farmId, from, to).stream()
        .map(MeasurementDocument::toDomain).collect(Collectors.toList());
  }

  @Override
  public List<Measurement> findAllByDeviceIdAndDateIsBetween(String deviceId, Date from, Date to) {
    String farmId = measurementDeviceMongoRepository.findById(deviceId)
        .map(MeasurementDeviceDocument::toDomain)
        .map(Device::getFarmId).get();
    return collection
        .find(and(eq(FARM_ID_FIELD, farmId), lt(DATE_FIELD, to), gt(DATE_FIELD, from)))
        .sort(descending(DATE_FIELD))
        .into(new LinkedList<>())
        .stream()
        .map(MeasurementDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Measurement> findTopByDate() {
    return Optional.ofNullable(MeasurementDocument.toDomain(collection
        .find()
        .sort(descending(DATE_FIELD))
        .first()));
  }

  @Override
  public Optional<Measurement> findDeviceLastMeasurement(String deviceId) {
    return Optional.ofNullable(MeasurementDocument.toDomain(collection
        .find(exists(Fields.concat(List.of(MEASUREMENTS_FIELD, deviceId))))
        .sort(descending(DATE_FIELD))
        .first()));
  }

}
