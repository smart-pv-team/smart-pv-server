package com.application.algorithms;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.management.farm.algorithm.Algorithm;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomAlgorithm extends ComparatorBasedAlgorithm implements Algorithm {

  @Override
  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices, Farm farm) {
    Random r = new Random();
    return super.updateDevicesStatus(measuredEnergy,
        devices,
        farm,
        Comparator.comparingInt((ConsumptionDevice d) -> r.nextInt()),
        Comparator.comparingInt((ConsumptionDevice d) -> r.nextInt())
    );
  }
}