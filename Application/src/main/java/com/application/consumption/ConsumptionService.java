package com.application.consumption;

import com.domain.model.consumption.Consumption;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.Farm;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.consumption.ConsumptionRepository;
import com.domain.ports.management.farm.DeviceGateway;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionService {

  private final ConsumptionRepository consumptionRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final DeviceGateway deviceGateway;

  private final ConsumptionStatisticsService consumptionStatisticsService;

  public ConsumptionService(ConsumptionRepository consumptionRepository,
      ConsumptionDeviceRepository consumptionDeviceRepository, DeviceGateway deviceGateway,
      ConsumptionStatisticsService consumptionStatisticsService) {
    this.consumptionRepository = consumptionRepository;
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.deviceGateway = deviceGateway;
    this.consumptionStatisticsService = consumptionStatisticsService;
  }

  public void sendUpdate(ConsumptionDevice consumptionDevice) {
    if (consumptionDevice.getIsOn()) {
      turnDeviceRequest(consumptionDevice, true);
      if (consumptionDevice.getControlParameters().lastStatus() != null) {
        deviceGateway.requestAction(consumptionDevice, consumptionDevice.getControlParameters().lastStatus());
      }
    } else {
      turnDeviceRequest(consumptionDevice, false);
    }
  }

  public void turnDeviceRequest(ConsumptionDevice consumptionDevice, Boolean status) {
    deviceGateway.requestOnOff(consumptionDevice, status);
  }

  public Consumption collectAndSaveDevicesStatus(Farm farm) {
    Consumption consumption = collectDevicesStatus(farm);
    consumptionStatisticsService.updateConsumptionStatistics(consumption);
    consumptionRepository.save(consumption);
    return consumption;
  }

  public boolean collectDeviceStatus(ConsumptionDevice consumptionDevice) {
    try {
      return deviceGateway.requestDevicesStatus(consumptionDevice).isOn();
    } catch (Exception e) {
      return false;
    }
  }

  public Consumption collectDevicesStatus(Farm farm) {
    List<String> activeDevices = consumptionDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .peek((device) -> device.setIsOn(collectDeviceStatus(device)))
        .filter(ConsumptionDevice::getIsOn)
        .map(Device::getId)
        .collect(Collectors.toList());

    return new Consumption(activeDevices, activeDevices.size(), new Date(), farm.id());
  }
}
