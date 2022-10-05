package smartpv.measurement.persistence.device;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDeviceRepositoryImpl implements MeasurementDeviceRepository {

  private final MeasurementDeviceMongoRepository measurementDeviceMongoRepository;

  public MeasurementDeviceRepositoryImpl(
      MeasurementDeviceMongoRepository measurementDeviceMongoRepository) {
    this.measurementDeviceMongoRepository = measurementDeviceMongoRepository;
  }


  @Override
  public List<MeasurementDeviceEntity> findAll() {
    return measurementDeviceMongoRepository.findAll();
  }

  @Override
  public List<MeasurementDeviceEntity> findAllByFarmId(String farmId) {
    return measurementDeviceMongoRepository.findAllByFarmId(farmId);
  }

  @Override
  public Optional<MeasurementDeviceEntity> getFirstById(String id) {
    return measurementDeviceMongoRepository.getFirstById(id);
  }

}
