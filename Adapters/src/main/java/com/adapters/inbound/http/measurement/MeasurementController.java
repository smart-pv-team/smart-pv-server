package com.adapters.inbound.http.measurement;

import com.adapters.inbound.http.DateRangeDto;
import com.adapters.inbound.http.Routing;
import com.adapters.outbound.persistence.measurement.MeasurementDocument;
import com.application.measurement.MeasurementStatisticsService;
import com.domain.model.management.farm.Device;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class MeasurementController {

  private final MeasurementRepository measurementRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementStatisticsService measurementStatisticsService;

  @GetMapping(Routing.Measurement.Devices.PATH)
  List<String> getDevices() {
    return measurementDeviceRepository.findAll().stream().map(Device::getId).collect(Collectors.toList());
  }

  @PatchMapping(Routing.Measurement.Devices.PATH)
  void updateDevice(@RequestBody MeasurementDevice measurementDevice) {
    measurementDeviceRepository.update(measurementDevice);
  }

  @PostMapping(Routing.Measurement.Devices.PATH)
  void saveDevice(@RequestBody MeasurementDevice measurementDevice) {
    measurementDeviceRepository.save(measurementDevice);
  }

  @DeleteMapping(Routing.Measurement.Devices.DeviceId.PATH)
  void deleteDevice(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    measurementDeviceRepository.delete(deviceId);
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.PATH)
  ResponseEntity<MeasurementDevice> getDevice(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementDeviceRepository
        .getFirstById(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Parameters.Name.PATH)
  ResponseEntity<String> getDeviceName(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementDeviceRepository
        .getFirstById(deviceId)
        .map(Device::getName)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Last.PATH)
  ResponseEntity<MeasurementResponseMapper> getDeviceMeasurement(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementRepository.findDeviceLastMeasurement(deviceId)
        .map(MeasurementDocument::fromDomain)
        .map(MeasurementResponseMapper::ofMeasurementEntity)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Range.PATH)
  List<MeasurementResponseMapper> getDeviceMeasurementRange(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
    return measurementRepository.findAllByDeviceIdAndDateIsBetween(
            deviceId,
            startDate,
            endDate)
        .stream()
        .map(measurementEntity ->
            new MeasurementResponseMapper(measurementEntity.getMeasurements().get(deviceId),
                measurementEntity.getDate()))
        .collect(Collectors.toList());
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Statistics.Sum.PATH)
  ResponseEntity<Long> getDeviceStatisticsSum(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementDeviceRepository.getFirstById(deviceId)
        .map(MeasurementDevice::getMeasuredEnergy)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Measurement.Farms.FarmId.Statistics.Sum.PATH)
  ResponseEntity<Long> getFarmStatisticsSum(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return ResponseEntity.ok(measurementDeviceRepository
        .findAllByFarmId(farmId)
        .stream()
        .mapToLong(MeasurementDevice::getMeasuredEnergy)
        .sum());
  }

  @GetMapping(Routing.Measurement.Farms.FarmId.Statistics.Period.PATH)
  ResponseEntity<Double> getFarmStatisticsPeriod(
      @PathVariable(Routing.FARM_ID_VARIABLE) String farmId,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate
  ) {
    return ResponseEntity.ok(measurementStatisticsService.getPeriodFarmEnergyStatisticsSum(farmId, startDate, endDate));
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Statistics.Period.PATH)
  ResponseEntity<Double> getDeviceStatisticsPeriod(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody DateRangeDto dateRange
  ) {
    return ResponseEntity.ok(
        measurementStatisticsService.getPeriodDeviceEnergyStatisticsSum(deviceId, dateRange.startDate(),
            dateRange.endDate()));
  }
}
