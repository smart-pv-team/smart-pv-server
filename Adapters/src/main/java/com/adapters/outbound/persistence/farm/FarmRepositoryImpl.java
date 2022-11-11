package com.adapters.outbound.persistence.farm;

import com.adapters.outbound.persistence.consumption.ConsumptionDeviceMongoRepository;
import com.adapters.outbound.persistence.measurement.MeasurementDeviceMongoRepository;
import com.domain.model.farm.Device;
import com.domain.model.farm.Farm;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.farm.FarmRepository;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class FarmRepositoryImpl implements FarmRepository {

  private final FarmMongoRepository farmMongoRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;
  private final ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository;

  public FarmRepositoryImpl(FarmMongoRepository farmMongoRepository,
      MeasurementDeviceRepository measurementDeviceRepository,
      ConsumptionDeviceRepository consumptionDeviceRepository,
      MeasurementDeviceMongoRepository measurementDeviceMongoRepository,
      ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository) {
    this.farmMongoRepository = farmMongoRepository;
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.measurementDeviceMongoRepository = measurementDeviceMongoRepository;
    this.consumptionDeviceMongoRepository = consumptionDeviceMongoRepository;
  }

  @Override
  public Optional<Farm> getById(String id) {
    return farmMongoRepository.getById(id).map(FarmDocument::toDomain);
  }

  @Override
  public List<Farm> findAll() {
    return farmMongoRepository.findAll().stream().map(FarmDocument::toDomain).collect(Collectors.toList());
  }

  @Override
  public List<Device> getAllFarmDevices(String farmId) {
    return Stream.concat(
            measurementDeviceRepository.findAllByFarmId(farmId).stream()
                .map((measurementDevice -> (Device) measurementDevice)),
            consumptionDeviceRepository.findAllByFarmId(farmId).stream()
                .map((consumptionDevice -> (Device) consumptionDevice)))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Device> getDeviceById(String farmId, String deviceId) {
    return getAllFarmDevices(farmId).stream().filter(device -> deviceId.equals(device.getId())).findFirst();
  }

  public Optional<DeviceEntity> getDeviceById(String deviceId) {
    Optional<DeviceEntity> measurementDevice = measurementDeviceMongoRepository.getFirstById(deviceId)
        .map(device -> device);
    Optional<DeviceEntity> consumptionDevice = consumptionDeviceMongoRepository.findById(deviceId)
        .map(device -> device);
    if (measurementDevice.isPresent()) {
      return measurementDevice;
    }
    return consumptionDevice;
  }
}
