package com.application.algorithms;

import com.application.DateTimeUtils;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class ComparatorBasedAlgorithm {

  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices, Farm farm, Comparator<ConsumptionDevice> turnOnComparator,
      Comparator<ConsumptionDevice> turnOffComparator) {
    Optional<ConsumptionDevice> deviceToChange;

    Float measurement = measuredEnergy.getMeasurement();
    if (measurement > farm.energyLimit()) {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter((device) -> !device.getIsOn())
          .filter((device) -> device.getControlParameters().lastStatusChange()
              .before(DateTimeUtils.subtractMinutes(measuredEnergy.getDate(), farm.minutesBetweenDeviceStatusSwitch())))
          .min(turnOnComparator)
          .map((device) -> {
            if (measurement > farm.energyLimit() + device.getControlParameters().powerConsumption()) {
              device.setIsOn(true);
            }
            return device;
          });
    } else {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter(ConsumptionDevice::getIsOn)
          .min(turnOffComparator)
          .filter((device) -> device.getControlParameters().lastStatusChange()
              .before(DateTimeUtils.subtractMinutes(measuredEnergy.getDate(), farm.minutesBetweenDeviceStatusSwitch())))
          .map((device) -> {
            device.setIsOn(false);
            return device;
          });
    }
    deviceToChange.map((device) -> {
      device.setControlParameters(device.getControlParameters().withLastStatusChange(measuredEnergy.getDate()));
      device.setControlParameters(device.getControlParameters().withStatusChange(null));
      return device;
    });
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
