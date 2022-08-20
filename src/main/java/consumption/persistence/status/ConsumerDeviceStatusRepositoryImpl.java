package consumption.persistence.status;

import org.springframework.stereotype.Component;

@Component
public class ConsumerDeviceStatusRepositoryImpl implements ConsumerDeviceStatusRepository {

  private final ConsumerDeviceStatusMongoRepository consumerDeviceStatusMongoRepository;

  public ConsumerDeviceStatusRepositoryImpl(
      ConsumerDeviceStatusMongoRepository consumerDeviceStatusMongoRepository) {
    this.consumerDeviceStatusMongoRepository = consumerDeviceStatusMongoRepository;
  }

  @Override
  public void save(ConsumerDeviceStatusEntity consumerDeviceStatusEntity) {
    consumerDeviceStatusMongoRepository.save(consumerDeviceStatusEntity);
  }
}
