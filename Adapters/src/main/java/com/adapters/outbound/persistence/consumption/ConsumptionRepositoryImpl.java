package com.adapters.outbound.persistence.consumption;

import static com.adapters.outbound.persistence.Fields.DATE_FIELD;
import static com.adapters.outbound.persistence.Fields.FARM_ID_FIELD;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;

import com.adapters.outbound.persistence.MongoUtils;
import com.domain.model.consumption.Consumption;
import com.domain.model.management.farm.Device;
import com.domain.ports.consumption.ConsumptionRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionRepositoryImpl implements ConsumptionRepository {

  private final ConsumptionMongoRepository consumptionMongoRepository;
  private final ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository;
  private final MongoCollection<ConsumptionDocument> collection;

  public ConsumptionRepositoryImpl(ConsumptionMongoRepository consumptionMongoRepository,
      ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository, MongoDatabase mongoDatabase) {
    this.consumptionMongoRepository = consumptionMongoRepository;
    this.consumptionDeviceMongoRepository = consumptionDeviceMongoRepository;
    this.collection = MongoUtils.getCollection(mongoDatabase, ConsumptionDocument.class);
  }

  @Override
  public List<Consumption> findAll() {
    return consumptionMongoRepository.findAll().stream().map(ConsumptionDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Consumption> findRecentEntities(String farmId, Date from, Date to) {
    return collection
        .find(and(eq(FARM_ID_FIELD, farmId), lt(DATE_FIELD, to), gt(DATE_FIELD, from)))
        .sort(descending(DATE_FIELD))
        .into(new LinkedList<>())
        .stream()
        .map(ConsumptionDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Consumption> findByDeviceIdAndDateBetween(String deviceId, Date from, Date to) {
    String farmId = consumptionDeviceMongoRepository
        .findById(deviceId)
        .map(ConsumptionDeviceDocument::toDomain)
        .map(Device::getFarmId).orElseThrow();
    return findRecentEntities(farmId, from, to);
  }

  @Override
  public Optional<Consumption> findLast(String farmId) {
    return Optional.ofNullable(ConsumptionDocument.toDomain(collection.find().sort(descending(DATE_FIELD)).first()));
  }

  @Override
  public void save(Consumption consumption) {
    consumptionMongoRepository.save(ConsumptionDocument.formDomain(consumption));
  }
}
