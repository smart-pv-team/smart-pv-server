package measurement.persistence.record;

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
  public void save(MeasurementEntity measurementEntity) {
    measurementMongoRepository.save(measurementEntity);
  }

  @Override
  public List<MeasurementEntity> getFromTimePeriod(String farmId, Date from,
      Date to) {
    return measurementMongoRepository.findAllByIdAndDateBetween(farmId, from, to);
  }

  @Override
  public List<MeasurementEntity> getAll() {
    return measurementMongoRepository.findAll();
  }

  @Override
  public List<MeasurementEntity> getAllByFarmId(String farmId) {
    return measurementMongoRepository.findAllByFarmId(farmId);
  }
}
