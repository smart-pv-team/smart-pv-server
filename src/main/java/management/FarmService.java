package management;

import java.util.List;
import management.persistence.FarmRepository;
import measurement.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmService {

  private final ManagementService managementService;
  private final MeasurementService measurementService;
  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(ManagementService managementService, FarmRepository farmRepository,
      MeasurementService measurementService) {
    this.managementService = managementService;
    this.measurementService = measurementService;
    this.farmRepository = farmRepository;
  }

  public String makeAllFarmsDevicesUpdate() {
    return String.join("\n",
        farmRepository.findAll().stream().map(managementService::updateDevicesStatus).toList());
  }

  public String makeAllFarmsDevicesMeasurement() {
    return String.valueOf(
        farmRepository.findAll().stream().map(measurementService::makeMeasurements)
            .mapToLong(List::size).sum());
  }
}