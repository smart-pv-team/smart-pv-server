package management.farm;

import consumption.ConsumerDeviceService;
import consumption.persistence.status.ConsumerDeviceStatusEntity;
import java.util.List;
import management.ManagementService;
import measurement.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmService {

  private final ManagementService managementService;
  private final MeasurementService measurementService;
  private final ConsumerDeviceService consumerDeviceService;
  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(ManagementService managementService, FarmRepository farmRepository,
      MeasurementService measurementService, ConsumerDeviceService consumerDeviceService) {
    this.managementService = managementService;
    this.measurementService = measurementService;
    this.farmRepository = farmRepository;
    this.consumerDeviceService = consumerDeviceService;
  }

  public String makeAllFarmsDevicesUpdate() {
    return String.join("\n",
        farmRepository.findAll().stream()
            .map(managementService::updateDevicesStatus)
            .toList());
  }

  public String makeAllFarmsDevicesMeasurement() {
    return String.valueOf(
        farmRepository.findAll().stream()
            .map(measurementService::makeMeasurements)
            .mapToLong(List::size)
            .sum());
  }

  public String collectAllFarmsDevicesStatus() {
    return String.valueOf(
        farmRepository.findAll().stream()
            .map(consumerDeviceService::collectDevicesStatus)
            .map(ConsumerDeviceStatusEntity::getActiveDevicesIds)
            .mapToLong(List::size)
            .sum());
  }
}