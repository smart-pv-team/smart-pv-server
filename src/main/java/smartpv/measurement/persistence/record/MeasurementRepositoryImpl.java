package smartpv.measurement.persistence.record;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Sorts.descending;
import static smartpv.server.conf.Fields.DATE_FIELD;
import static smartpv.server.conf.Fields.FARM_ID_FIELD;
import static smartpv.server.conf.Fields.MEASUREMENTS_FIELD;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import smartpv.management.device.Device;
import smartpv.measurement.persistence.device.MeasurementDeviceMongoRepository;
import smartpv.server.conf.Fields;
import smartpv.server.conf.MongoUtils;

@Component
public class MeasurementRepositoryImpl implements MeasurementRepository {

  private final MeasurementMongoRepository measurementMongoRepository;
  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;
  private final MongoCollection<MeasurementEntity> collection;

  public MeasurementRepositoryImpl(MeasurementMongoRepository measurementMongoRepository,
      MeasurementDeviceMongoRepository measurementDeviceMongoRepository, MongoDatabase mongoDatabase) {
    this.measurementMongoRepository = measurementMongoRepository;
    this.measurementDeviceMongoRepository = measurementDeviceMongoRepository;
    this.collection = MongoUtils.getCollection(mongoDatabase, MeasurementEntity.class);
  }

  @Override
  public List<MeasurementEntity> findAll() {
    return measurementMongoRepository.findAll();
  }

  @Override
  public void save(MeasurementEntity measurementEntity) {
    measurementMongoRepository.save(measurementEntity);
  }

  @Override
  public List<MeasurementEntity> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to) {
    return measurementMongoRepository.findAllByFarmIdAndDateIsBetween(farmId, from, to);
  }

  @Override
  public List<MeasurementEntity> findAllByDeviceIdAndDateIsBetween(String deviceId, Date from, Date to) {
    String farmId = measurementDeviceMongoRepository.findById(deviceId).map(Device::getFarmId).get();
    return collection
        .find(and(eq(FARM_ID_FIELD, farmId), lt(DATE_FIELD, to), gt(DATE_FIELD, from)))
        .sort(descending(DATE_FIELD))
        .into(new LinkedList<>());
  }

  @Override
  public Optional<MeasurementEntity> findTopByDate() {
    return Optional.ofNullable(collection
        .find()
        .sort(descending(DATE_FIELD))
        .first());
  }

  @Override
  public Optional<MeasurementEntity> findDeviceLastMeasurement(String deviceId) {
    return Optional.ofNullable(collection
        .find(exists(Fields.concat(List.of(MEASUREMENTS_FIELD, deviceId))))
        .sort(descending(DATE_FIELD))
        .first());
  }

}
