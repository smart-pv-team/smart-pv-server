package consumption;

import management.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.conf.EnvNames;

@Component
public class ConsumerDeviceActivityScheduler {

  private final FarmService farmService;
  private final String activityManagementCron;

  @Autowired
  public ConsumerDeviceActivityScheduler(
      FarmService farmService,
      @Value("${" + EnvNames.MANAGEMENT_CRON + "}") String activityManagementCron
  ) {
    this.farmService = farmService;
    this.activityManagementCron = activityManagementCron;
  }

  @Scheduled(cron = "0 * * * * *")
  public void updateConsumerDevices() {
    System.out.println("[CONSUMER-DEVICES-SCHEDULER] " + farmService.updateAllFarmDeviceStatus());
  }
}
