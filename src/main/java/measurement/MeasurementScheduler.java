package measurement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.conf.EnvNames;

@Component
public class MeasurementScheduler {

  private final MeasurementService measurementService;
  private final String measurementCron;

  @Autowired
  public MeasurementScheduler(
      MeasurementService measurementService,
      @Value("${" + EnvNames.MEASUREMENT_CRON + "}") String measurementCron
  ) {
    this.measurementService = measurementService;
    this.measurementCron = measurementCron;
  }

  @Scheduled(cron = "0 * * * * *")
  public void scheduleMeasurements() {
    System.out.println("[MEASURING-DEVICES-SCHEDULER] " + measurementService.makeMeasurements());
  }

}
