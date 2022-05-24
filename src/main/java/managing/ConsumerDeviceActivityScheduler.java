package managing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.conf.EnvNames;

@Component
public class ConsumerDeviceActivityScheduler {
    private final ManagementService managementService;
    private final String activityManagementCron;
    @Autowired
    public ConsumerDeviceActivityScheduler(
            ManagementService managementService,
            @Value("${" + EnvNames.MANAGEMENT_CRON + "}") String activityManagementCron
    ) {
        this.managementService = managementService;
        this.activityManagementCron = activityManagementCron;
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void updateConsumerDevices() {
        System.out.println("[CONSUMER-DEVICES-SCHEDULER] " + managementService.updateDevicesStatus());
    }
}
