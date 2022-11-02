package smartpv.consumption.persistence.record;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;
import static smartpv.server.conf.Fields.DATE_FIELD;
import static smartpv.server.conf.Fields.FARM_ID_FIELD;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import smartpv.consumption.persistence.device.ConsumptionDeviceMongoRepository;
import smartpv.management.device.Device;
import smartpv.server.conf.MongoUtils;

@Component
public class ConsumptionRepositoryImpl implements ConsumptionRepository {

  private final ConsumptionMongoRepository consumptionMongoRepository;
  private final ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository;
  private final MongoCollection<ConsumptionEntity> collection;

  public ConsumptionRepositoryImpl(ConsumptionMongoRepository consumptionMongoRepository,
      ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository, MongoDatabase mongoDatabase) {
    this.consumptionMongoRepository = consumptionMongoRepository;
    this.consumptionDeviceMongoRepository = consumptionDeviceMongoRepository;
    this.collection = MongoUtils.getCollection(mongoDatabase, ConsumptionEntity.class);
  }

  @Override
  public List<ConsumptionEntity> findAll() {
    return consumptionMongoRepository.findAll();
  }

  @Override
  public List<ConsumptionEntity> findRecentEntities(String farmId, Date from, Date to) {
    return collection
        .find(and(eq(FARM_ID_FIELD, farmId), lt(DATE_FIELD, to), gt(DATE_FIELD, from)))
        .sort(descending(DATE_FIELD))
        .into(new LinkedList<>());
  }

  @Override
  public List<ConsumptionEntity> findByDeviceIdAndDateBetween(String deviceId, Date from, Date to) {
    String farmId = consumptionDeviceMongoRepository.findById(deviceId).map(Device::getFarmId).get();
    return findRecentEntities(farmId, from, to);
  }

  @Override
  public Optional<ConsumptionEntity> findLast(String farmId) {
    return Optional.ofNullable(collection.find().sort(descending(DATE_FIELD)).first());
  }

  @Override
  public void save(ConsumptionEntity consumptionEntity) {
    consumptionMongoRepository.save(consumptionEntity);
  }
}
