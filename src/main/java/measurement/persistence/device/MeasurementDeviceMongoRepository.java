package measurement.persistence.device;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementDeviceMongoRepository extends
    MongoRepository<MeasurementDeviceEntity, String> {

  List<MeasurementDeviceEntity> findAll();

  List<MeasurementDeviceEntity> findAllByFarmId(String farmId);

  MeasurementDeviceEntity getFirstById(String id);
}
