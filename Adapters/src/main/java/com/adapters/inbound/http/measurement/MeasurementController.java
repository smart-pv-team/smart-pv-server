package com.adapters.inbound.http.measurement;

import com.adapters.inbound.http.Routing;
import com.adapters.outbound.persistence.measurement.MeasurementDocument;
import com.domain.model.farm.Device;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MeasurementController {

  private final MeasurementRepository measurementRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;

  @Autowired
  MeasurementController(MeasurementRepository measurementRepository,
      MeasurementDeviceRepository measurementDeviceRepository) {
    this.measurementRepository = measurementRepository;
    this.measurementDeviceRepository = measurementDeviceRepository;
  }

  @GetMapping(Routing.Measurement.Devices.PATH)
  List<String> getDevices() {
    return measurementDeviceRepository.findAll().stream().map(Device::getId).collect(Collectors.toList());
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

}
