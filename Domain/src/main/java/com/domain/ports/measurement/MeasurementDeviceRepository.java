package com.domain.ports.measurement;

import com.domain.model.measurement.MeasurementDevice;
import java.util.List;
import java.util.Optional;

public interface MeasurementDeviceRepository {

  List<MeasurementDevice> findAll();

  List<MeasurementDevice> findAllByFarmId(String farmId);

  Optional<MeasurementDevice> getFirstById(String id);

  void save(MeasurementDevice measurementDevice);

  void update(MeasurementDevice measurementDevice);

  void delete(String measurementDeviceId);

  void saveMeasurementStatistics(String measurementDeviceId, Long measured);

  void setIsOn(MeasurementDevice measurementDevice, Boolean isOn);
}
