package com.application.farm;

import com.application.DateTimeUtils;
import com.application.algorithms.StringToAlgorithm;
import com.application.consumption.ConsumptionService;
import com.domain.model.consumption.Consumption;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.farm.CounterGateway;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final ConsumptionService consumptionService;
  private final CounterGateway counter;
  private final MeasurementRepository measurementRepository;

  @Autowired
  public ManagementService(ConsumptionDeviceRepository consumptionDeviceRepository,
      ConsumptionService consumptionService, CounterGateway counter, MeasurementRepository measurementRepository) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.consumptionService = consumptionService;
    this.counter = counter;
    this.measurementRepository = measurementRepository;
  }

  public List<ConsumptionDevice> updateDevices(Farm farm) {
    Consumption consumption = consumptionService.collectDevicesStatus(farm);
    List<ConsumptionDevice> activeDevicesRequestResponse = consumptionDeviceRepository
        .findAllByFarmIdAndIdIsIn(farm.id(), consumption.getActiveDevicesIds());

    List<ConsumptionDevice> allDevices = consumptionDeviceRepository.findAllByFarmId(farm.id());
    List<ConsumptionDevice> allDevicesWithUpdatedLock = updateDevicesLock(activeDevicesRequestResponse,
        allDevices);
    List<Measurement> measurementEntitiesToAverage = measurementRepository.findAllByFarmIdAndDateIsBetween(
        farm.id(), DateTimeUtils.subtractMinutes(consumption.getDate(), farm.minutesToAverageMeasurement()),
        DateTimeUtils.addMinutes(consumption.getDate(), 1));
    if (measurementEntitiesToAverage.size() == 0) {
      return allDevicesWithUpdatedLock;
    }
    Measurement measuredEnergy = new Measurement(
        farm.id(),
        getAverageMeasurement(measurementEntitiesToAverage).floatValue(),
        Map.of(),
        consumption.getDate());
    List<ConsumptionDevice> allDevicesWithUpdatedLockAndStatus = StringToAlgorithm.stringToAlgorithm(
            farm.algorithmType())
        .updateDevicesStatus(
            measuredEnergy,
            allDevicesWithUpdatedLock,
            farm);

    List<ConsumptionDevice> updatedDevices = allDevicesWithUpdatedLockAndStatus.stream()
        .filter((device) -> {
          boolean isActive = activeDevicesRequestResponse.stream()
              .map(ConsumptionDevice::getId)
              .toList()
              .contains(device.getId());
          return device.getIsOn() && !isActive
              || !device.getIsOn() && isActive;
        }).collect(Collectors.toList());
    consumptionDeviceRepository.saveAll(allDevicesWithUpdatedLockAndStatus);
    updatedDevices.forEach((device) -> consumptionService.turnDeviceRequest(device, device.getIsOn()));

    return updatedDevices;
  }

  private List<ConsumptionDevice> updateDevicesLock(List<ConsumptionDevice> activeDevices,
      List<ConsumptionDevice> allDevices) {
    allDevices.forEach((device) -> {
      boolean isDeviceOnRequestResponse = activeDevices.contains(device);
      if (!device.getControlParameters().lock().isLocked()) {
        if ((device.getIsOn() && !isDeviceOnRequestResponse)
            || (!device.getIsOn() && isDeviceOnRequestResponse)) {
          device.setControlParameters(device.getControlParameters().withLock(true, DateTimeUtils.getNow()));
        }
      } else {
        if (device.getControlParameters().lock().date().after(DateTimeUtils.getNow())) {
          device.setControlParameters(device.getControlParameters().withLock(false, DateTimeUtils.getNow()));
        }
      }
      device.setIsOn(isDeviceOnRequestResponse);
    });
    return allDevices;
  }

  private Double getAverageMeasurement(List<Measurement> measurementEntitiesToAverage) {
    return counter.countAverage(
        measurementEntitiesToAverage
            .stream()
            .mapToDouble(Measurement::getMeasurement)
            .boxed()
            .collect(Collectors.toList())
    );
  }
}
