package com.application.farm;

import com.application.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ManagementScheduler {

  private final FarmService farmService;
  private final SystemProperties systemProperties;

  @Autowired
  public ManagementScheduler(
      FarmService farmService,
      SystemProperties systemProperties
  ) {
    this.farmService = farmService;
    this.systemProperties = systemProperties;
  }

  @Scheduled(cron = "${system.cron.management}")
  public void updateConsumerDevices() {
    if (systemProperties.getManageDevices()) {
      System.out.println("[MANAGING-SCHEDULER] " + farmService.makeAllFarmsDevicesUpdate());
    } else {
      System.out.println("[MANGING-SCHEDULER] disabled");
    }
  }
}