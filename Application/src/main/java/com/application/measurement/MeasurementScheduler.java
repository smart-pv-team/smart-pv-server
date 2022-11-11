package com.application.measurement;

import com.application.SystemProperties;
import com.application.farm.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MeasurementScheduler {

  private final FarmService farmService;
  private final SystemProperties systemProperties;

  @Autowired
  public MeasurementScheduler(
      FarmService farmService,
      SystemProperties systemProperties
  ) {
    this.farmService = farmService;
    this.systemProperties = systemProperties;
  }

  @Scheduled(cron = "${system.cron.measurement}")
  public void scheduleMeasurements() {
    if (systemProperties.getMeasure()) {
      System.out.println(
          "[MEASURING-DEVICES-SCHEDULER] " + farmService.makeAllFarmsDevicesMeasurement()
              + " measurements");
    } else {
      System.out.println("[MEASURING-DEVICES-SCHEDULER] disabled");
    }
  }

}
