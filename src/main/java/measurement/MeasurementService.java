package measurement;

import java.util.Date;
import java.util.List;
import management.persistence.FarmEntity;
import measurement.persistence.device.MeasurementDeviceEntity;
import measurement.persistence.device.MeasurementDeviceRepository;
import measurement.persistence.record.MeasurementEntity;
import measurement.persistence.record.MeasurementRepository;
import measurement.persistence.sum.MeasurementSumEntity;
import measurement.persistence.sum.MeasurementSumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementSumRepository measurementSumRepository;
  private final MeasurementRequester measurementRequester;

  @Autowired
  public MeasurementService(MeasurementRepository measurementRepository,
      MeasurementDeviceRepository measurementDeviceRepository,
      MeasurementRequester measurementRequester,
      MeasurementSumRepository measurementSumRepository) {
    this.measurementRepository = measurementRepository;
    this.measurementRequester = measurementRequester;
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.measurementSumRepository = measurementSumRepository;
  }

  public List<MeasurementEntity> makeMeasurements(FarmEntity farm) {
    List<MeasurementEntity> measurements = measurementDeviceRepository.findAllByFarmId(farm.id())
        .stream()
        .map(this::requestMeasurement).toList();
    MeasurementSumEntity sumMeasurement = new MeasurementSumEntity(
        farm.id(),
        measurements.stream().map(MeasurementEntity::getMeasurement).reduce((float) 0, Float::sum),
        new Date()
    );

    measurementRepository.save(measurements);
    measurementSumRepository.save(List.of(sumMeasurement));
    return measurements;
  }


  private MeasurementEntity requestMeasurement(MeasurementDeviceEntity measurementDeviceEntity) {
    try {
      return measurementRequester.getMeasurement(measurementDeviceEntity)
          .toMeasurementEntity(measurementDeviceEntity.id());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
