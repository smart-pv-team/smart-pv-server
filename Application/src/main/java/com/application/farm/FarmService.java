package com.application.farm;

import com.application.consumption.ConsumptionService;
import com.application.measurement.MeasurementService;
import com.domain.model.consumption.Consumption;
import com.domain.model.management.farm.AlgorithmType;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.management.farm.FarmRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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
    return farmRepository.findAll().stream()
        .filter(Farm::running)
        .map(managementService::updateDevices)
        .flatMap(Collection::stream)
        .map((device) -> "(%s,%s)".formatted(device.getId(), device.getIsOn()))
        .collect(Collectors.joining("\n"));
  }

  public String makeAllFarmsDevicesMeasurement() {
    return String.valueOf(
        farmRepository.findAll().stream()
            .map(measurementService::makeAndSaveMeasurement)
            .mapToDouble(Measurement::getMeasurement)
            .sum());
  }

  public String collectAllFarmsDevicesStatus() {
    return String.valueOf(
        farmRepository.findAll().stream()
            .map(consumptionService::collectAndSaveDevicesStatus)
            .map(Consumption::getActiveDevicesIds)
            .mapToLong(List::size)
            .sum());
  }

  public void changeAlgorithmType(String farmId, AlgorithmType algorithmType) {
    farmRepository.setFarmAlgorithm(farmId, algorithmType);
    farmRepository.setFarmRunning(farmId, false);
  }
}