package com.springapplication;

import com.application.consumption.ConsumptionService;
import com.domain.model.consumption.Consumption;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.farm.FarmRepository;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import java.util.List;
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
            Consumption::getActiveDevicesIds).flatMap(List::stream).toList();
    consumptionDeviceRepository.findAll()
        .forEach((device) -> device.setIsOn(activeDevicesIds.contains(device.getId())));
  }

  public void printLogs() {
    System.out.println(measurementDeviceRepository.findAll());
    System.out.println(consumptionDeviceRepository.findAll());
  }
}
