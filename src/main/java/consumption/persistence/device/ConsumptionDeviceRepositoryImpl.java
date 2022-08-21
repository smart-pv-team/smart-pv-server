package consumption.persistence.device;

import consumption.ControlParameters;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import management.farm.FarmEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionDeviceRepositoryImpl implements ConsumptionDeviceRepository {

  private final ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository;

  public ConsumptionDeviceRepositoryImpl(
      ConsumptionDeviceMongoRepository consumptionDeviceMongoRepository) {
    this.consumptionDeviceMongoRepository = consumptionDeviceMongoRepository;
  }

  @Override
  public List<ConsumptionDeviceEntity> findAllByFarmId(String farmId) {
    return consumptionDeviceMongoRepository.findAllByFarmId(farmId);
  }

  @Override
  public List<ConsumptionDeviceEntity> findAll() {
    return consumptionDeviceMongoRepository.findAll();
  }

  @Override
  public Optional<Boolean> isDeviceOn(String id) {
    return Optional.of(consumptionDeviceMongoRepository.findById(id)
        .orElseThrow()
        .getIsOn());
  }

  @Override
  public void setDeviceOn(String id, boolean newStatus) {
    ConsumptionDeviceEntity device = consumptionDeviceMongoRepository.findById(id).orElseThrow();
    device.setIsOn(newStatus);
    consumptionDeviceMongoRepository.save(device);
  }

  @Override
  public void setDeviceParameters(String id, ControlParameters controlParameters) {
    ConsumptionDeviceEntity consumptionDeviceEntity = consumptionDeviceMongoRepository.findById(
        id).get();
    consumptionDeviceEntity.setControlParameters(controlParameters);
    consumptionDeviceMongoRepository.save(consumptionDeviceEntity);
  }

  @Override
  public Optional<ControlParameters> getDeviceParameters(String id) {
    return Optional.ofNullable(
        consumptionDeviceMongoRepository.findById(id).get().getControlParameters());
  }

  //TODO: rewrite to collections
  @Override
  public Optional<ConsumptionDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm) {
    return consumptionDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(d -> !d.getIsOn())
        .min(Comparator.comparing((e) -> e.getControlParameters().getPriority()));
  }

  @Override
  public Optional<ConsumptionDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm) {
    return consumptionDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(ConsumptionDeviceEntity::getIsOn)
        .max(Comparator.comparing((e) -> e.getControlParameters().getPriority()));
  }
}
