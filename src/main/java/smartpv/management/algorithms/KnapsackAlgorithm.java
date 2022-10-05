package smartpv.management.algorithms;

import java.util.List;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.record.ConsumptionEntity;
import smartpv.management.farm.persistance.FarmEntity;

public class KnapsackAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDeviceEntity> updateDevicesStatus(Float measuredEnergy, List<ConsumptionDeviceEntity> devices,
      FarmEntity farm, List<ConsumptionEntity> recentConsumptionEntities) {

    Float availableEnergy = measuredEnergy + (float) devices.stream().filter(ConsumptionDeviceEntity::getIsOn)
        .mapToDouble((device) -> device.getControlParameters().powerConsumption()).sum();
    //TODO
    return devices;
  }
}
