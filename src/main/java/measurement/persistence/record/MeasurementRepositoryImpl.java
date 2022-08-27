package measurement.persistence.record;

import java.util.Date;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MeasurementRepositoryImpl implements MeasurementRepository {

  private final MeasurementMongoRepository measurementMongoRepository;

  public MeasurementRepositoryImpl(MeasurementMongoRepository measurementMongoRepository) {
    this.measurementMongoRepository = measurementMongoRepository;
  }


  @Override
  public void save(MeasurementEntity measurementEntity) {
    measurementMongoRepository.save(measurementEntity);
  }

  @Override
  public Optional<MeasurementEntity> findTopByFarmIdAndDateAfter(String farmId, Date after) {
    return measurementMongoRepository.findTopByFarmIdAndDateAfter(farmId, after);
  }

}
