package com.application.consumption;

import com.application.SystemProperties;
import com.application.farm.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionScheduler {

  private final FarmService farmService;
  private final SystemProperties systemProperties;

  @Autowired
  public ConsumptionScheduler(
      FarmService farmService,
      SystemProperties systemProperties
  ) {
    this.farmService = farmService;
    this.systemProperties = systemProperties;
  }

  @Scheduled(cron = "${system.cron.collectDevicesStatus}")
  public void updateConsumerDevices() {
    if (systemProperties.getCollectDevicesStatus()) {
      System.out.println("[CONSUMER-DEVICES-COLLECT-STATUS-SCHEDULER] "
          + farmService.collectAllFarmsDevicesStatus() + " active devices");
    } else {
      System.out.println("[CONSUMER-DEVICES-COLLECT-STATUS-SCHEDULER] disabled");
    }
  }
}
