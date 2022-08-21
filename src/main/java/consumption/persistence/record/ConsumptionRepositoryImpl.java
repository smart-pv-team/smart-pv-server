package consumption.persistence.record;

import org.springframework.stereotype.Component;

@Component
public class ConsumptionRepositoryImpl implements ConsumptionRepository {

  private final ConsumptionMongoRepository consumptionMongoRepository;

  public ConsumptionRepositoryImpl(
      ConsumptionMongoRepository consumptionMongoRepository) {
    this.consumptionMongoRepository = consumptionMongoRepository;
  }

  @Override
  public void save(ConsumptionEntity consumptionEntity) {
    consumptionMongoRepository.save(consumptionEntity);
  }
}
