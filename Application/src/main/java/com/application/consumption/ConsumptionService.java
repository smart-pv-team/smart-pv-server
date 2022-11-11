package com.application.consumption;

import com.domain.model.consumption.Consumption;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Device;
import com.domain.model.farm.Farm;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.consumption.ConsumptionRepository;
import com.domain.ports.farm.DeviceGateway;
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

  public void turnDeviceRequest(ConsumptionDevice consumptionDevice, Boolean status) {
    deviceGateway.requestOnOff(consumptionDevice, status);
  }

  public Consumption collectAndSaveDevicesStatus(Farm farm) {
    Consumption consumption = collectDevicesStatus(farm);
    consumptionStatisticsService.updateConsumptionStatistics(consumption);
    consumptionRepository.save(consumption);
    return consumption;
  }

  public Consumption collectDevicesStatus(Farm farm) {
    List<String> activeDevices = consumptionDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .peek((device) -> device.setIsOn(deviceGateway.requestDevicesStatus(device).isOn()))
        .filter(ConsumptionDevice::getIsOn)
        .map(Device::getId)
        .collect(Collectors.toList());

    return new Consumption(activeDevices, activeDevices.size(), new Date(), farm.id());
  }
}
