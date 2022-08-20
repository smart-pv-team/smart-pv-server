package consumption.persistence.device;

import consumption.ControlParameters;
import java.util.List;
import java.util.Optional;
import management.farm.FarmEntity;

public interface ConsumerDeviceRepository {

  List<ConsumerDeviceEntity> findAllByFarmId(String farmId);

  Optional<Boolean> isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ControlParameters controlParameters);

  Optional<ControlParameters> getDeviceParameters(
      String id);

  Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm);

  Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm);

}
