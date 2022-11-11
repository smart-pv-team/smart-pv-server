package com.application.algorithms;

import com.application.DateTimeUtils;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.farm.Algorithm;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PowerHysteresisPriorityAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices,
      Farm farm) {
    Float measurement = measuredEnergy.getMeasurement();
    Optional<ConsumptionDevice> deviceToChange = devices.stream()
        .filter((device) -> !device.getControlParameters().lock().isLocked())
        .filter((device) -> !device.getIsOn())
        .sorted(Comparator.comparing((e) -> e.getControlParameters().priority()))
        .max(Comparator.comparing((e) -> e.getControlParameters().minHysteresis()))
        .filter((device) -> measurement < device.getControlParameters().minHysteresis())
        .map((device) -> {
          device.setIsOn(false);
          device.setControlParameters(device.getControlParameters().withLastStatusChange(DateTimeUtils.getNow()));
          return device;
        });

    if (deviceToChange.isEmpty()) {
      deviceToChange = devices.stream()
          .filter((device) -> !device.getControlParameters().lock().isLocked())
          .filter((device) -> !device.getIsOn())
          .sorted(Comparator.comparing((e) -> -e.getControlParameters().priority()))
          .min(Comparator.comparing((e) -> e.getControlParameters().maxHysteresis()))
          .filter((device) -> measurement > device.getControlParameters().maxHysteresis())
          .map((device) -> {
            device.setIsOn(true);
            device.setControlParameters(device.getControlParameters().withLastStatusChange(DateTimeUtils.getNow()));
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
