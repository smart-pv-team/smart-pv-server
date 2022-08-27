package management.farm;

import consumption.ConsumptionService;
import consumption.persistence.record.ConsumptionEntity;
import java.util.List;
import management.ManagementService;
import measurement.MeasurementService;
import measurement.persistence.record.MeasurementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmService {

  private final ManagementService managementService;
  private final MeasurementService measurementService;
  private final ConsumptionService consumptionService;
  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(ManagementService managementService, FarmRepository farmRepository,
      MeasurementService measurementService, ConsumptionService consumptionService) {
    this.managementService = managementService;
    this.measurementService = measurementService;
    this.farmRepository = farmRepository;
    this.consumptionService = consumptionService;
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
            .map(measurementService::makeAndSaveMeasurement)
            .mapToDouble(MeasurementEntity::getMeasurement)
            .sum());
  }

  public String collectAllFarmsDevicesStatus() {
    return String.valueOf(
        farmRepository.findAll().stream()
            .map(consumptionService::collectAndSaveDevicesStatus)
            .map(ConsumptionEntity::getActiveDevicesIds)
            .mapToLong(List::size)
            .sum());
  }
}