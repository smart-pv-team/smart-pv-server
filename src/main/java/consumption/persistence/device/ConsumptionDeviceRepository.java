package consumption.persistence.device;

import consumption.ControlParameters;
import java.util.List;
import java.util.Optional;
import management.farm.persistance.FarmEntity;

public interface ConsumptionDeviceRepository {

  Optional<ConsumptionDeviceEntity> findById(String id);

  List<ConsumptionDeviceEntity> findAllByFarmId(String farmId);

  List<ConsumptionDeviceEntity> findAll();

  Optional<Boolean> isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ControlParameters controlParameters);

  Optional<ControlParameters> getDeviceParameters(
      String id);

  Optional<ConsumptionDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm);

  Optional<ConsumptionDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm);

  List<ConsumptionDeviceEntity> findAllByIdIsIn(List<String> ids);
}
