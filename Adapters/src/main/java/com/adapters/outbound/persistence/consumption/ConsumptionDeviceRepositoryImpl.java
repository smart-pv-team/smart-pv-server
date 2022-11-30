package com.adapters.outbound.persistence.consumption;

import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.consumption.ControlParameters;
import com.domain.model.farm.Farm;
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
    if (findById(consumptionDevice.getId()).isEmpty()) {
      consumptionDevice.setCreationDate(new Date());
      consumptionDeviceMongoRepository.save(
          ConsumptionDeviceDocument.fromDomain(consumptionDevice.withWorkingHours(0L)));
    }
  }

  @Override
  public void update(ConsumptionDevice consumptionDevice) {
    Optional<ConsumptionDeviceDocument> old = consumptionDeviceMongoRepository.findById(consumptionDevice.getId());
    if (old.isPresent()) {
      consumptionDevice.setCreationDate(old.get().getCreationDate());
      consumptionDevice.setWorkingHours(old.get().getWorkingHours());
      consumptionDeviceMongoRepository.save(ConsumptionDeviceDocument.fromDomain(consumptionDevice));
    }
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
  public void setDeviceParameters(String id, ControlParameters controlParameters) {
    ConsumptionDeviceDocument consumptionDevice = consumptionDeviceMongoRepository
        .findById(id)
        .get();
    consumptionDevice.setControlParameters(ControlParametersEntity.fromDomain(controlParameters));
    consumptionDeviceMongoRepository.save(consumptionDevice);
  }

  @Override
  public Optional<ControlParameters> getDeviceParameters(String id) {
    return Optional.ofNullable(
        ControlParametersEntity.toDomain(
            consumptionDeviceMongoRepository
                .findById(id)
                .get()
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
        .get();
    Long workingHours = duration + consumptionDevice.getWorkingHours();
    ConsumptionDevice consumptionDeviceUpdated = consumptionDevice.withWorkingHours(workingHours);
    consumptionDeviceMongoRepository.save(ConsumptionDeviceDocument.fromDomain(consumptionDeviceUpdated));
  }
}
