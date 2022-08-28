package server.conf;

import consumption.ConsumptionService;
import consumption.persistence.device.ConsumptionDeviceRepository;
import consumption.persistence.record.ConsumptionEntity;
import java.util.List;
import management.farm.FarmRepository;
import measurement.persistence.device.MeasurementDeviceRepository;
import org.springframework.stereotype.Component;

@Component
public class BeforeStart {

  private final FarmRepository farmRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final ConsumptionService consumptionService;
  private final MeasurementDeviceRepository measurementDeviceRepository;

  public BeforeStart(FarmRepository farmRepository,
      ConsumptionDeviceRepository consumptionDeviceRepository,
      ConsumptionService consumptionService,
      MeasurementDeviceRepository measurementDeviceRepository) {
    this.farmRepository = farmRepository;
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.consumptionService = consumptionService;
    this.measurementDeviceRepository = measurementDeviceRepository;
  }

  public void updateConsumingDeviceStatusInDatabase() {
    List<String> activeDevicesIds = farmRepository.findAll().stream()
        .map(consumptionService::collectDevicesStatus).map(
            ConsumptionEntity::getActiveDevicesIds).flatMap(List::stream).toList();
    consumptionDeviceRepository.findAll()
        .forEach((device) -> device.setIsOn(activeDevicesIds.contains(device.getId())));
  }

  public void printLogs() {
    System.out.println(measurementDeviceRepository.findAll());
    System.out.println(consumptionDeviceRepository.findAll());
  }
}
