package com.adapters.outbound.persistence.management.farm;

import com.adapters.outbound.persistence.consumption.ConsumptionDeviceMongoRepository;
import com.adapters.outbound.persistence.measurement.MeasurementDeviceMongoRepository;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.AlgorithmType;
import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.management.farm.FarmRepository;
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
  public List<ConsumptionDevice> getAllFarmConsumptionDevices(String farmId) {
    return consumptionDeviceRepository.findAllByFarmId(farmId);
  }

  @Override
  public List<MeasurementDevice> getAllFarmMeasurementDevices(String farmId) {
    return measurementDeviceRepository.findAllByFarmId(farmId);
  }

  @Override
  public Optional<Device> getDeviceById(String deviceId) {
    return Stream.concat(
            measurementDeviceRepository
                .findAll()
                .stream()
                .map((measurementDevice -> (Device) measurementDevice)),
            consumptionDeviceRepository
                .findAll()
                .stream()
                .map((consumptionDevice -> (Device) consumptionDevice)))
        .filter(device -> deviceId.equals(device.getId()))
        .findFirst();
  }

  @Override
  public void setFarmAlgorithm(String farmId, AlgorithmType algorithm) {
    FarmDocument farm = farmMongoRepository.getById(farmId).orElseThrow();
    farmMongoRepository.save(farm.withAlgorithmType(algorithm));
  }

  @Override
  public void setFarmRunning(String farmId, Boolean running) {
    FarmDocument farm = farmMongoRepository.getById(farmId).orElseThrow();
    farmMongoRepository.save(farm.withRunning(running));
  }

  @Override
  public String save(Farm farm) {
    FarmDocument currentFarm = farmMongoRepository.getById(farm.id()).orElse(null);
    Boolean running = false;
    if (currentFarm != null) {
      if (currentFarm.running() != null) {
        running = currentFarm.running();
      }
    }
    return farmMongoRepository.save(FarmDocument.fromDomain(farm)
        .withRunning(running)
        .withAlgorithmType(farm.algorithmType())).id();
  }
}
