package consumption;

import java.util.Optional;
import management.FarmEntity;

public interface ConsumerDeviceRepository {

  Optional<Boolean> isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ConsumerDeviceParametersMapper consumerDeviceParametersMapper);

  Optional<ConsumerDeviceParametersMapper> getDeviceParameters(
      String id);

  Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm);

  Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm);

}
