package consumption.persistence;

import consumption.ControlParameters;
import java.util.Comparator;
import java.util.Optional;
import management.persistence.FarmEntity;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDeviceRepositoryImpl implements ConsumerDeviceRepository {

  private final ConsumerDeviceMongoRepository consumerDeviceMongoRepository;

  public ConsumerDeviceRepositoryImpl(ConsumerDeviceMongoRepository consumerDeviceMongoRepository) {
    this.consumerDeviceMongoRepository = consumerDeviceMongoRepository;
  }

  @Override
  public Optional<Boolean> isDeviceOn(String id) {
    return Optional.of(consumerDeviceMongoRepository.findById(id)
        .orElseThrow()
        .getIsOn());
  }

  @Override
  public void setDeviceOn(String id, boolean newStatus) {
    ConsumerDeviceEntity device = consumerDeviceMongoRepository.findById(id).orElseThrow();
    device.setIsOn(newStatus);
    consumerDeviceMongoRepository.save(device);
  }

  @Override
  public void setDeviceParameters(String id, ControlParameters controlParameters) {
    ConsumerDeviceEntity consumerDeviceEntity = consumerDeviceMongoRepository.findById(
        id).get();
    consumerDeviceEntity.setControlParameters(controlParameters);
    consumerDeviceMongoRepository.save(consumerDeviceEntity);
  }

  @Override
  public Optional<ControlParameters> getDeviceParameters(String id) {
    return Optional.ofNullable(
        consumerDeviceMongoRepository.findById(id).get().getControlParameters());
  }

  //TODO: rewrite to collections
  @Override
  public Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm) {
    return consumerDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(d -> !d.getIsOn())
        .min(Comparator.comparing((e) -> e.getControlParameters().getPriority()));
  }

  @Override
  public Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm) {
    return consumerDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(ConsumerDeviceEntity::getIsOn)
        .max(Comparator.comparing((e) -> e.getControlParameters().getPriority()));
  }
}
