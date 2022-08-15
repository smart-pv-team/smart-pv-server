package measurement.persistence.record;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MeasurementRepositoryImpl implements MeasurementRepository{
  private final MeasurementMongoRepository measurementMongoRepository;

  public MeasurementRepositoryImpl(MeasurementMongoRepository measurementMongoRepository) {
    this.measurementMongoRepository = measurementMongoRepository;
  }

  @Override
  public void save(List<MeasurementEntity> measurementEntities) {
    measurementMongoRepository.saveAll(measurementEntities);
  }

}
