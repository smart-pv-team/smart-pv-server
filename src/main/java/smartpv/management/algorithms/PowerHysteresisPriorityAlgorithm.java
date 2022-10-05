package smartpv.management.algorithms;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.record.ConsumptionEntity;
import smartpv.management.farm.persistance.FarmEntity;

public class PowerHysteresisPriorityAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDeviceEntity> updateDevicesStatus(Float measuredEnergy, List<ConsumptionDeviceEntity> devices,
      FarmEntity farm, List<ConsumptionEntity> recentConsumptionEntities) {
    Optional<ConsumptionDeviceEntity> deviceToChange = devices.stream()
        .filter((device) -> !device.getControlParameters().lock().isLocked())
        .filter((device) -> !device.getIsOn())
        .sorted(Comparator.comparing((e) -> e.getControlParameters().priority()))
        .max(Comparator.comparing((e) -> e.getControlParameters().minHysteresis()))
        .filter((device) -> measuredEnergy < device.getControlParameters().minHysteresis())
        .map((device) -> {
          device.setIsOn(false);
          return device;
        });

    if (deviceToChange.isEmpty()) {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter((device) -> !device.getIsOn())
          .sorted(Comparator.comparing((e) -> -e.getControlParameters().priority()))
          .min(Comparator.comparing((e) -> e.getControlParameters().maxHysteresis()))
          .filter((device) -> measuredEnergy > device.getControlParameters().maxHysteresis())
          .map((device) -> {
            device.setIsOn(true);
            return device;
          });
    }

    deviceToChange.ifPresent(
        (deviceToChangePresent) -> devices.set(
            devices.stream()
                .filter((device) -> device.getId().equals(deviceToChangePresent.getId()))
                .findFirst()
                .map((devices::indexOf))
                .orElse(0),
            deviceToChangePresent
        ));

    return devices;
  }
}
