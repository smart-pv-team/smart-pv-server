package measurement;

import management.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.conf.EnvNames;

@Component
public class MeasurementScheduler {

  private final FarmService farmService;
  private final String measurementCron;

  @Autowired
  public MeasurementScheduler(
      FarmService farmService,
      @Value("${" + EnvNames.MEASUREMENT_CRON + "}") String measurementCron
  ) {
    this.farmService = farmService;
    this.measurementCron = measurementCron;
  }

  @Scheduled(cron = "0 * * * * *")
  public void scheduleMeasurements() {
    System.out.println(
        "[MEASURING-DEVICES-SCHEDULER] " + farmService.makeAllFarmsDevicesMeasurement()
            + " measurements");
  }

}
