package consumption;

import management.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.conf.SystemProperties;

@Component
public class ConsumerDeviceCollectStatusScheduler {

  private final FarmService farmService;
  private final SystemProperties systemProperties;

  @Autowired
  public ConsumerDeviceCollectStatusScheduler(
      FarmService farmService,
      SystemProperties systemProperties
  ) {
    this.farmService = farmService;
    this.systemProperties = systemProperties;
  }

  @Scheduled(cron = "${system.cron.collectDevicesStatus}")
  public void updateConsumerDevices() {
    if (systemProperties.getCollectDevicesStatus()) {
      System.out.println("[CONSUMER-DEVICES-COLLECT-STATUS-SCHEDULER] ");
    } else {
      System.out.println("[CONSUMER-DEVICES-COLLECT-STATUS-SCHEDULER] disabled");
    }
  }
}
