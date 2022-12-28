package com.domain.ports.management.farm;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.AlgorithmType;
import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.MeasurementDevice;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository {

  Optional<Farm> getById(String id);

  List<Farm> findAll();

  List<Device> getAllFarmDevices(String farmId);

  List<ConsumptionDevice> getAllFarmConsumptionDevices(String farmId);

  List<MeasurementDevice> getAllFarmMeasurementDevices(String farmId);

  Optional<Device> getDeviceById(String deviceId);

  void setFarmAlgorithm(String farmId, AlgorithmType algorithm);

  void setFarmRunning(String farmId, Boolean running);

  String save(Farm farm);
}
