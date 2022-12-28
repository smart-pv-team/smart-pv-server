package com.adapters.outbound.persistence.consumption;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.consumption.ControlParameters;
import com.domain.model.consumption.ControlParameters.Lock;
import com.domain.model.management.farm.Farm;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ConsumptionDeviceRepositoryImpl implements ConsumptionDeviceRepository {

  private final ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository;

  @Override
  public void save(ConsumptionDevice consumptionDevice) {
    if (consumptionDevice.getId() == null || findById(consumptionDevice.getId()).isEmpty()) {
      consumptionDevice = consumptionDevice
          .withCreationDate(new Date())
          .withIsOn(false)
          .withControlParameters(
              new ControlParameters(0,
                  0f,
                  new Lock(false, new Date()),
                  null,
                  null)
          );
      consumptionDeviceMongoRepository.save(ConsumptionDeviceDocument.fromDomain(consumptionDevice));
    }
  }

  @Override
  public void update(ConsumptionDevice consumptionDevice) {
    if (consumptionDevice.getIsOn() == null) {
      return;
    }
    ConsumptionDeviceDocument old = consumptionDeviceMongoRepository
        .findById(consumptionDevice.getId())
        .orElseThrow();
    consumptionDevice.setCreationDate(old.getCreationDate());
    consumptionDevice.setWorkingHours(old.getWorkingHours());
    consumptionDevice.setControlParameters(ControlParametersEntity.toDomain(old.getControlParameters()));
    consumptionDevice.withIsOn(old.getIsOn());
    consumptionDeviceMongoRepository.save(ConsumptionDeviceDocument.fromDomain(consumptionDevice));
  }

  @Override
  public void delete(String consumptionDeviceId) {
    consumptionDeviceMongoRepository.deleteById(consumptionDeviceId);
  }

  @Override
  public Optional<ConsumptionDevice> findById(String id) {
    return consumptionDeviceMongoRepository.findById(id).map(ConsumptionDeviceDocument::toDomain);
  }

  @Override
  public void saveAll(List<ConsumptionDevice> devices) {
    consumptionDeviceMongoRepository
        .saveAll(devices.stream().map(ConsumptionDeviceDocument::fromDomain).collect(Collectors.toList()));
  }

  @Override
  public List<ConsumptionDevice> findAllByFarmId(String farmId) {
    return consumptionDeviceMongoRepository
        .findAllByFarmId(farmId).stream().map(ConsumptionDeviceDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<ConsumptionDevice> findAll() {
    return consumptionDeviceMongoRepository
        .findAll()
        .stream()
        .map(ConsumptionDeviceDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Boolean> isDeviceOn(String id) {
    return Optional.of(consumptionDeviceMongoRepository
        .findById(id)
        .orElseThrow()
        .getIsOn());
  }

  @Override
  public void setDeviceOn(String id, boolean newStatus) {
    ConsumptionDeviceDocument device = consumptionDeviceMongoRepository.findById(id).orElseThrow();
    device.setIsOn(newStatus);
    consumptionDeviceMongoRepository.save(device);
  }

  @Override
  public void setDevicePriority(String id, Integer priority) {
    ConsumptionDeviceDocument device = consumptionDeviceMongoRepository.findById(id).orElseThrow();
    ControlParametersEntity controlParameters = device.getControlParameters().withPriority(priority);
    device.setControlParameters(controlParameters);
    consumptionDeviceMongoRepository.save(device);
  }

  @Override
  public void setDevicePowerConsumption(String id, Float powerConsumption) {
    ConsumptionDeviceDocument device = consumptionDeviceMongoRepository.findById(id).orElseThrow();
    ControlParametersEntity controlParameters = device.getControlParameters().withPowerConsumption(powerConsumption);
    device.setControlParameters(controlParameters);
    consumptionDeviceMongoRepository.save(device);
  }

  @Override
  public void setDeviceParameters(String id, ControlParameters controlParameters) {
    ConsumptionDeviceDocument consumptionDevice = consumptionDeviceMongoRepository
        .findById(id)
        .orElseThrow();
    consumptionDevice.setControlParameters(ControlParametersEntity.fromDomain(controlParameters));
    consumptionDeviceMongoRepository.save(consumptionDevice);
  }

  @Override
  public Optional<ControlParameters> getDeviceParameters(String id) {
    return Optional.ofNullable(
        ControlParametersEntity.toDomain(
            consumptionDeviceMongoRepository
                .findById(id)
                .orElseThrow()
                .getControlParameters())
    );
  }

  @Override
  public Optional<ConsumptionDevice> findHighestPriorityOffDevice(Farm farm) {
    return consumptionDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(d -> !d.getIsOn())
        .min(Comparator.comparing((e) -> e.getControlParameters().priority()))
        .map(ConsumptionDeviceDocument::toDomain);
  }

  @Override
  public Optional<ConsumptionDevice> findLowestPriorityOnDevice(Farm farm) {
    return consumptionDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(ConsumptionDeviceDocument::getIsOn)
        .max(Comparator.comparing((e) -> e.getControlParameters().priority()))
        .map(ConsumptionDeviceDocument::toDomain);
  }

  @Override
  public List<ConsumptionDevice> findAllByFarmIdAndIdIsIn(String farmId, List<String> ids) {
    return consumptionDeviceMongoRepository
        .findAllByFarmIdAndIdIsIn(farmId, ids)
        .stream()
        .map(ConsumptionDeviceDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void saveConsumptionStatistic(String deviceId, Long duration) {
    ConsumptionDevice consumptionDevice = consumptionDeviceMongoRepository
        .findById(deviceId)
        .map(ConsumptionDeviceDocument::toDomain)
        .orElseThrow();
    Long workingHours = duration + consumptionDevice.getWorkingHours();
    ConsumptionDevice consumptionDeviceUpdated = consumptionDevice.withWorkingHours(workingHours);
    consumptionDeviceMongoRepository.save(ConsumptionDeviceDocument.fromDomain(consumptionDeviceUpdated));
  }
}
