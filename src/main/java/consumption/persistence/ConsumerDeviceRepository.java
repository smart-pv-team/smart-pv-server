package consumption.persistence;

import consumption.ControlParameters;
import java.util.Optional;
import management.persistence.FarmEntity;

public interface ConsumerDeviceRepository {

  Optional<Boolean> isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ControlParameters controlParameters);

  Optional<ControlParameters> getDeviceParameters(
      String id);

  Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm);

  Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm);

}
