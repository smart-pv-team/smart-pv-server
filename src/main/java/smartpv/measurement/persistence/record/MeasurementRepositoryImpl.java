package smartpv.measurement.persistence.record;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MeasurementRepositoryImpl implements MeasurementRepository {

  private final MeasurementMongoRepository measurementMongoRepository;

  public MeasurementRepositoryImpl(MeasurementMongoRepository measurementMongoRepository) {
    this.measurementMongoRepository = measurementMongoRepository;
  }

  @Override
  public List<MeasurementEntity> findAll() {
    return measurementMongoRepository.findAll();
  }

  @Override
  public void save(MeasurementEntity measurementEntity) {
    measurementMongoRepository.save(measurementEntity);
  }

  @Override
  public List<MeasurementEntity> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to) {
    return measurementMongoRepository.findAllByFarmIdAndDateIsBetween(farmId, from, to);
  }

  @Override
  public Optional<MeasurementEntity> findTopByDateAndIdIsNot(String id) {
    return measurementMongoRepository.findTopByDateAndIdIsNot(id);
  }
}
