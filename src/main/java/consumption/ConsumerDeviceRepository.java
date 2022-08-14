package consumption;

import java.util.List;
import java.util.Optional;
import managment.FarmEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumerDeviceRepository extends MongoRepository<ConsumerDeviceEntity, String> {

  List<ConsumerDeviceEntity> findAllByFarmId(String farmId);

  boolean isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ConsumerDeviceParametersMapper consumerDeviceParametersMapper);

  ConsumerDeviceParametersMapper getDeviceParameters(
      String id);

  Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm);

  Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm);
}
