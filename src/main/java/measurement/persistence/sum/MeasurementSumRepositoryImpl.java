package measurement.persistence.sum;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MeasurementSumRepositoryImpl implements MeasurementSumRepository {

  private final MeasurementSumMongoRepository measurementSumMongoRepository;

  public MeasurementSumRepositoryImpl(MeasurementSumMongoRepository measurementSumMongoRepository) {
    this.measurementSumMongoRepository = measurementSumMongoRepository;
  }

  @Override
  public void save(List<MeasurementSumEntity> measurementSumEntity) {
    measurementSumMongoRepository.saveAll(measurementSumEntity);
  }

  @Override
  public List<MeasurementSumEntity> getFromTimePeriod(String farmId, Date from,
      Date to) {
    return measurementSumMongoRepository.findAllByIdAndDateBetween(farmId, from, to);
  }

  @Override
  public List<MeasurementSumEntity> getAll() {
    return measurementSumMongoRepository.findAll();
  }

  @Override
  public List<MeasurementSumEntity> getAllByFarmId(String farmId) {
    return measurementSumMongoRepository.findAllByFarmId(farmId);
  }
}
