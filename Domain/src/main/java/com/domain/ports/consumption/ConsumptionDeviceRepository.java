package com.domain.ports.consumption;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.consumption.ControlParameters;
import com.domain.model.farm.Farm;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionDeviceRepository {

  void save(ConsumptionDevice consumptionDevice);

  void update(ConsumptionDevice consumptionDevice);

  void delete(String consumptionDeviceId);

  Optional<ConsumptionDevice> findById(String id);

  void saveAll(List<ConsumptionDevice> devices);

  List<ConsumptionDevice> findAllByFarmId(String farmId);

  List<ConsumptionDevice> findAll();

  Optional<Boolean> isDeviceOn(String id);

  void setDeviceOn(String id, boolean newStatus);

  void setDeviceParameters(String id,
      ControlParameters controlParameters);

  Optional<ControlParameters> getDeviceParameters(
      String id);

  Optional<ConsumptionDevice> findHighestPriorityOffDevice(Farm farm);

  Optional<ConsumptionDevice> findLowestPriorityOnDevice(Farm farm);

  List<ConsumptionDevice> findAllByFarmIdAndIdIsIn(String farmId, List<String> ids);

  void saveConsumptionStatistic(String deviceId, Long duration);
}
