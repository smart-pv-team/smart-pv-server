package consumption.persistence;

import consumption.ConsumerDeviceParametersMapper;
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
        .isOn());
  }

  @Override
  public void setDeviceOn(String id, boolean newStatus) {
    ConsumerDeviceEntity device = consumerDeviceMongoRepository.findById(id).orElseThrow();
    device.setOn(newStatus);
    consumerDeviceMongoRepository.save(device);
  }

  @Override
  public void setDeviceParameters(String id,
      ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
    ConsumerDeviceEntity oldConsumerDeviceEntity = consumerDeviceMongoRepository.findById(
        id).orElseThrow();
    consumerDeviceMongoRepository.save(
        oldConsumerDeviceEntity.withParameters(consumerDeviceParametersMapper)
            .withId(id));
  }

  @Override
  public Optional<ConsumerDeviceParametersMapper> getDeviceParameters(
      String id) {
    return consumerDeviceMongoRepository.findById(id)
        .map(ConsumerDeviceParametersMapper::ofConsumerDevice);
  }

  //TODO: rewrite to collections
  @Override
  public Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm) {
    return consumerDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(d -> !d.isOn())
        .min(Comparator.comparing(ConsumerDeviceEntity::getPriority));
  }

  @Override
  public Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm) {
    return consumerDeviceMongoRepository.findAllByFarmId(farm.id())
        .stream()
        .filter(ConsumerDeviceEntity::isOn)
        .max(Comparator.comparing(ConsumerDeviceEntity::getPriority));
  }
}
