package measuring;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;

@RestController
public class MeasurementController {

  private final MeasurementService measurementService;

  @Autowired
  public MeasurementController(MeasurementService measurementService) {
    this.measurementService = measurementService;
  }

  @GetMapping(Routing.Measuring.Farm.FarmId.VALUE)
  public List<MeasurementMapper> getDeviceParameters(
      @PathVariable(Routing.FARM_ID_VARIABLE) String deviceId) {
    return measurementService.getAllSumMeasurements().stream().map(
        MeasurementMapper::ofMeasurementSumEntity).toList();
  }

}
