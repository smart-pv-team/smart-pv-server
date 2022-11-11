package com.application.algorithms;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.farm.Algorithm;
import java.util.List;

public class KnapsackAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices,
      Farm farm) {

    Float availableEnergy =
        measuredEnergy.getMeasurement() + (float) devices.stream().filter(ConsumptionDevice::getIsOn)
            .mapToDouble((device) -> device.getControlParameters().powerConsumption()).sum();
    //TODO
    return devices;
  }
}
