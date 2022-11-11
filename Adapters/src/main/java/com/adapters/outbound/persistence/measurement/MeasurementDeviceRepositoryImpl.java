package com.adapters.outbound.persistence.measurement;

import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDeviceRepositoryImpl implements MeasurementDeviceRepository {

  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;

  public MeasurementDeviceRepositoryImpl(
      MeasurementDeviceMongoRepository measurementDeviceMongoRepository) {
    this.measurementDeviceMongoRepository = measurementDeviceMongoRepository;
  }


  @Override
  public List<MeasurementDevice> findAll() {
    return measurementDeviceMongoRepository.findAll().stream().map(MeasurementDeviceDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<MeasurementDevice> findAllByFarmId(String farmId) {
    return measurementDeviceMongoRepository.findAllByFarmId(farmId).stream().map(MeasurementDeviceDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<MeasurementDevice> getFirstById(String id) {
    return measurementDeviceMongoRepository.getFirstById(id).map(MeasurementDeviceDocument::toDomain);
  }

  @Override
  public void save(MeasurementDevice measurementDevice) {
    measurementDeviceMongoRepository.save(MeasurementDeviceDocument.fromDomain(measurementDevice));
  }

  @Override
  public void saveMeasurementStatistics(String measurementDeviceId, Long measured) {
    MeasurementDeviceDocument measurementDevice = measurementDeviceMongoRepository
        .findById(measurementDeviceId).get();
    Long energy = measured + measurementDevice.getMeasuredEnergy();
    MeasurementDevice measurementDeviceUpdated = MeasurementDeviceDocument
        .toDomain(measurementDevice)
        .withMeasuredEnergy(energy);
    measurementDeviceMongoRepository.save(MeasurementDeviceDocument.fromDomain(measurementDeviceUpdated));
  }
}
