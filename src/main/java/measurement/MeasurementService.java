package measurement;

import java.util.Date;
import java.util.List;
import management.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final MeasurementSumRepository measurementSumRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementRequester measurementRequester;
  private final FarmRepository farmRepository;

  @Autowired
  public MeasurementService(MeasurementRepository measurementRepository,
      MeasurementDeviceRepository measurementDeviceRepository,
      MeasurementRequester measurementRequester,
      FarmRepository farmRepository, MeasurementSumRepository measurementSumRepository) {
    this.measurementRepository = measurementRepository;
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.measurementRequester = measurementRequester;
    this.farmRepository = farmRepository;
    this.measurementSumRepository = measurementSumRepository;
  }

  //TODO: replace with repository
  public List<MeasurementEntity> makeMeasurements() {
    List<MeasurementEntity> allMeasurements = measurementDeviceRepository
        .findAll()
        .stream()
        .map(this::requestMeasurement).toList();
    List<MeasurementSumEntity> allSumMeasurements =
        farmRepository.findAll().stream()
            .map(farmEntity -> new MeasurementSumEntity(
                farmEntity.name(),
                allMeasurements.stream()
                    .filter(measurementEntity -> measurementDeviceRepository.getFirstById(
                        measurementEntity.getDeviceId()).farm().equals(farmEntity.name()))
                    .map(MeasurementEntity::getMeasurement).reduce((float) 0, Float::sum),
                new Date()
            )).toList();

    saveMeasurements(allMeasurements);
    saveSumMeasurements(allSumMeasurements);
    return allMeasurements;
  }

  private void saveMeasurements(List<MeasurementEntity> measurementEntities) {
    measurementRepository.saveAll(measurementEntities);
  }

  private void saveSumMeasurements(List<MeasurementSumEntity> measurementSumEntity) {
    measurementSumRepository.saveAll(measurementSumEntity);
  }

  private MeasurementEntity requestMeasurement(MeasurementDeviceEntity measurementDeviceEntity) {
    try {
      return measurementRequester.getMeasurement(measurementDeviceEntity)
          .toMeasurementEntity(measurementDeviceEntity.id());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public List<MeasurementSumEntity> getMeasurementSumEntityFromTimePeriod(String farmId, Date from,
      Date to) {
    return measurementSumRepository.findAllByIdAndDateBetween(farmId, from, to);
  }

  public List<MeasurementSumEntity> getAllSumMeasurements() {
    return measurementSumRepository.findAll();
  }
}
