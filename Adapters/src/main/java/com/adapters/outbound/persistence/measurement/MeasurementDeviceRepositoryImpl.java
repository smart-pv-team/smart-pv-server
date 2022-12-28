package com.adapters.outbound.persistence.measurement;

import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MeasurementDeviceRepositoryImpl implements MeasurementDeviceRepository {

  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;

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
    if (getFirstById(measurementDevice.getId()).isEmpty()) {
      measurementDevice.setCreationDate(new Date());
      measurementDeviceMongoRepository.save(
          MeasurementDeviceDocument.fromDomain(measurementDevice.withMeasuredEnergy(0L)));
    }
  }

  @Override
  public void update(MeasurementDevice measurementDevice) {
    Optional<MeasurementDeviceDocument> old = measurementDeviceMongoRepository.findById(measurementDevice.getId());
    if (old.isPresent()) {
      measurementDevice.setCreationDate(old.get().getCreationDate());
      measurementDevice.setMeasuredEnergy(old.get().getMeasuredEnergy());
      measurementDeviceMongoRepository.save(MeasurementDeviceDocument.fromDomain(measurementDevice));
    }
  }

  @Override
  public void delete(String measurementDeviceId) {
    measurementDeviceMongoRepository.deleteById(measurementDeviceId);
  }

  @Override
  public void saveMeasurementStatistics(String measurementDeviceId, Long measured) {
    MeasurementDeviceDocument measurementDevice = measurementDeviceMongoRepository
        .findById(measurementDeviceId).orElseThrow();
    Long energy = measured + measurementDevice.getMeasuredEnergy();
    MeasurementDevice measurementDeviceUpdated = MeasurementDeviceDocument
        .toDomain(measurementDevice)
        .withMeasuredEnergy(energy);
    measurementDeviceMongoRepository.save(MeasurementDeviceDocument.fromDomain(measurementDeviceUpdated));
  }

  @Override
  public void setIsOn(MeasurementDevice measurementDevice, Boolean isOn) {
    measurementDevice.setIsOn(isOn);
    measurementDeviceMongoRepository.save(MeasurementDeviceDocument.fromDomain(measurementDevice));
  }
}
