package smartpv.management.algorithms;

import java.util.List;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.record.ConsumptionEntity;
import smartpv.management.farm.persistance.FarmEntity;

public interface Algorithm {

  List<ConsumptionDeviceEntity> updateDevicesStatus(Float measuredEnergy, List<ConsumptionDeviceEntity> devices,
      FarmEntity farm, List<ConsumptionEntity> recentConsumptionEntities);
}
