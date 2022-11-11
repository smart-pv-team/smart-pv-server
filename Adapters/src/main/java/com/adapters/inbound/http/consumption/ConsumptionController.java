package com.adapters.inbound.http.consumption;

import com.adapters.inbound.http.Routing;
import com.adapters.inbound.http.Routing.Consumption.Devices;
import com.domain.model.consumption.Consumption;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.consumption.ControlParameters;
import com.domain.model.farm.Device;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.consumption.ConsumptionRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
class ConsumptionController {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final ConsumptionRepository consumptionRepository;

  @Autowired
  ConsumptionController(ConsumptionDeviceRepository consumptionDeviceRepository,
      ConsumptionRepository consumptionRepository) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.consumptionRepository = consumptionRepository;
  }

  @GetMapping(Devices.PATH)
  List<String> getDevices() {
    return consumptionDeviceRepository
        .findAll()
        .stream()
        .map(Device::getId)
        .collect(Collectors.toList());
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.PATH)
  ResponseEntity<ConsumptionDevice> getDevice(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .findById(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Parameters.PATH)
  ResponseEntity<ControlParameters> getDeviceParameters(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .getDeviceParameters(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(Routing.Consumption.Devices.DeviceId.Parameters.PATH)
  void setDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ControlParameters controlParameters) {
    consumptionDeviceRepository.setDeviceParameters(deviceId, controlParameters);
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Parameters.IsOn.PATH)
  ResponseEntity<Boolean> getDeviceParametersIsOn(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .isDeviceOn(deviceId).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(Routing.Consumption.Devices.DeviceId.Parameters.IsOn.PATH)
  void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ConsumptionIsOnDto consumptionIsOnDto) {
    consumptionDeviceRepository.setDeviceOn(deviceId, consumptionIsOnDto.isOn());
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Range.PATH)
  Map<Date, Boolean> getRangeDeviceActiveStatus(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
    return consumptionRepository
        .findByDeviceIdAndDateBetween(deviceId, startDate, endDate)
        .stream()
        .collect(
            Collectors.toMap(
                Consumption::getDate,
                consumption -> consumption.getActiveDevicesIds().contains(deviceId))
        );
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Last.PATH)
  ResponseEntity<Date> getLastDeviceActiveStatus(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionRepository
        .findLast(consumptionDeviceRepository.findById(deviceId).map(Device::getFarmId).get())
        .map(Consumption::getDate)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Statistics.Sum.PATH)
  ResponseEntity<Long> getDeviceStatisticsSum(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .findById(deviceId).map(ConsumptionDevice::getWorkingHours)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Consumption.Farms.FarmId.Statistics.Sum.PATH)
  ResponseEntity<Long> getFarmStatisticsSum(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return ResponseEntity.ok(consumptionDeviceRepository
        .findAllByFarmId(farmId)
        .stream()
        .mapToLong(ConsumptionDevice::getWorkingHours)
        .sum());
  }
}
