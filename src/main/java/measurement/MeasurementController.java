package measurement;

import java.util.List;
import measurement.persistence.sum.MeasurementSumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;
import server.conf.Routing.Measurement;

@RestController
public class MeasurementController {

  private final MeasurementSumRepository measurementSumRepository;

  @Autowired
  public MeasurementController(MeasurementSumRepository measurementSumRepository) {
    this.measurementSumRepository = measurementSumRepository;
  }

  @GetMapping(Measurement.Farm.FarmId.VALUE)
  public List<MeasurementMapper> getDeviceParameters(
      @PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return measurementSumRepository.getAllByFarmId(farmId).stream().map(
        MeasurementMapper::ofMeasurementSumEntity).toList();
  }

}
