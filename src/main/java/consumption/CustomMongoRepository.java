package consumption;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import managment.FarmEntity;

public abstract class CustomMongoRepository implements ConsumerDeviceRepository {

  @Override
  public boolean isDeviceOn(String id) {
    return findById(id)
        .orElseThrow()
        .isOn();
  }

  @Override
  public void setDeviceOn(String id, boolean newStatus) {
    ConsumerDeviceEntity device = findById(id).orElseThrow();
    device.setOn(newStatus);
    save(device);
  }

  @Override
  public List<String> getAllDevicesIds() {
    return findAll()
        .stream()
        .map(ConsumerDeviceEntity::getId)
        .collect(Collectors.toList());
  }

  @Override
  public void setDeviceParameters(String id,
      ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
    ConsumerDeviceEntity oldConsumerDeviceEntity = findById(
        id).orElseThrow();
    save(
        oldConsumerDeviceEntity.withParameters(consumerDeviceParametersMapper)
            .withId(id));
  }

  @Override
  public ConsumerDeviceParametersMapper getDeviceParameters(
      String id) {
    ConsumerDeviceEntity oldConsumerDeviceEntity = findById(id)
        .orElseThrow();
    return ConsumerDeviceParametersMapper.ofConsumerDevice(oldConsumerDeviceEntity);
  }

  @Override
  public Optional<ConsumerDeviceEntity> findHighestPriorityOffDevice(FarmEntity farm) {
    return findAllByFarmId(farm.id())
        .stream()
        .filter(d -> !d.isOn())
        .min(Comparator.comparing(ConsumerDeviceEntity::getPriority));
  }

  @Override
  public Optional<ConsumerDeviceEntity> findLowestPriorityOnDevice(FarmEntity farm) {
    return findAllByFarmId(farm.id())
        .stream()
        .filter(ConsumerDeviceEntity::isOn)
        .max(Comparator.comparing(ConsumerDeviceEntity::getPriority));
  }
}
