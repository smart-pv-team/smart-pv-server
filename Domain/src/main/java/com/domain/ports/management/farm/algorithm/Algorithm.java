package com.domain.ports.management.farm.algorithm;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import java.util.List;

public interface Algorithm {

  List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices,
      Farm farm);


}
