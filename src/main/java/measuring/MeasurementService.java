package measuring;

import deviceController.FarmRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final MeasurementSumRepository measurementSumRepository;
  private final MeasuringDeviceRepository measuringDeviceRepository;
  private final MeasuringRequester measuringRequester;
  private final FarmRepository farmRepository;
  private float latestTotalMeasurements;

  @Autowired
  public MeasurementService(MeasurementRepository measurementRepository,
      MeasuringDeviceRepository measuringDeviceRepository, MeasuringRequester measuringRequester,
      FarmRepository farmRepository, MeasurementSumRepository measurementSumRepository) {
    this.measurementRepository = measurementRepository;
    this.measuringDeviceRepository = measuringDeviceRepository;
    this.measuringRequester = measuringRequester;
    this.farmRepository = farmRepository;
    this.measurementSumRepository = measurementSumRepository;
  }

  public List<MeasurementEntity> makeMeasurements() {
    List<MeasurementEntity> allMeasurements = measuringDeviceRepository
        .findAll()
        .stream()
        .map(this::requestMeasurement).toList();
    List<MeasurementSumEntity> allSumMeasurements =
        farmRepository.findAll().stream()
            .map(farmEntity -> new MeasurementSumEntity(
                farmEntity.getName(),
                allMeasurements.stream()
                    .filter(measurementEntity -> measuringDeviceRepository.getFirstById(
                        measurementEntity.getDeviceId()).farm().equals(farmEntity.getName()))
                    .map(MeasurementEntity::getMeasurement).reduce((float) 0, Float::sum)
                , new Date()
            )).toList();

    saveMeasurements(allMeasurements);
    saveSumMeasurements(allSumMeasurements);
    latestTotalMeasurements = allMeasurements
        .stream()
        .map(MeasurementEntity::getMeasurement)
        .reduce((float) 0, Float::sum);
    return allMeasurements;
  }

  private void saveMeasurements(List<MeasurementEntity> measurementEntities) {
    measurementRepository.saveAll(measurementEntities);
  }
  private void saveSumMeasurements(List<MeasurementSumEntity> measurementSumEntity) {
    measurementSumRepository.saveAll(measurementSumEntity);
  }

  private MeasurementEntity requestMeasurement(MeasuringDeviceEntity measuringDeviceEntity) {
    try {
      return measuringRequester.getMeasurement(measuringDeviceEntity)
          .toMeasurementEntity(measuringDeviceEntity.id());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public float getLatestTotalMeasurements() {
    return latestTotalMeasurements;
  }
  public List<MeasurementSumEntity> getMeasurementSumEntityFromTimePeriod(String farmId, Date from, Date to){
    return measurementSumRepository.findAllByIdAndDateBetween(farmId,from,to);
  }

  public List<MeasurementSumEntity> getAllSumMeasurements(){
    return measurementSumRepository.findAll();
  }
}
