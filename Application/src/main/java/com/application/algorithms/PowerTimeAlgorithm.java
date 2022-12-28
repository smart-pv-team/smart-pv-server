package com.application.algorithms;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.management.farm.algorithm.Algorithm;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PowerTimeAlgorithm extends ComparatorBasedAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices, Farm farm) {
    return super.updateDevicesStatus(measuredEnergy,
        devices,
        farm,
        Comparator.comparing((ConsumptionDevice e) -> e.getControlParameters().lastStatusChange()),
        Comparator.comparing((ConsumptionDevice e) -> e.getControlParameters().lastStatusChange()).reversed()
    );
  }
}
