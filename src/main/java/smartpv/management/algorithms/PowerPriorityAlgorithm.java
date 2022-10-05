package smartpv.management.algorithms;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.record.ConsumptionEntity;
import smartpv.management.farm.persistance.FarmEntity;

public class PowerPriorityAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDeviceEntity> updateDevicesStatus(Float measuredEnergy,
      List<ConsumptionDeviceEntity> devices, FarmEntity farm, List<ConsumptionEntity> recentConsumptionEntities) {
    Optional<ConsumptionDeviceEntity> deviceToChange;

    if (measuredEnergy > farm.energyLimit()) {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter((device) -> !device.getIsOn())
          .max(Comparator.comparing((e) -> e.getControlParameters().priority()))
          .filter((device) -> !recentConsumptionEntities.stream()
              .map((entity) -> entity.getActiveDevicesIds().contains(device.getId()))
              .reduce(false, (e1, e2) -> e1 || e2))
          .map((device) -> {
            if (measuredEnergy > farm.energyLimit() + device.getControlParameters().powerConsumption()) {
              device.setIsOn(true);
            }
            return device;
          });
    } else {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter(ConsumptionDeviceEntity::getIsOn)
          .min(Comparator.comparing((e) -> e.getControlParameters().priority()))
          .filter((device) -> !recentConsumptionEntities.stream()
              .map((entity) -> !entity.getActiveDevicesIds().contains(device.getId()))
              .reduce(false, (e1, e2) -> e1 || e2))
          .map((device) -> {
            device.setIsOn(false);
            return device;
          });
    }
    deviceToChange.ifPresent(
        (deviceToChangePresent) -> devices.set(
            devices
                .stream()
                .filter((device) -> device.getId().equals(deviceToChangePresent.getId()))
                .findFirst()
                .map((devices::indexOf))
                .orElse(0),
            deviceToChangePresent
        )
    );
    return devices;
  }
}
