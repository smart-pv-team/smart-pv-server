package measurement;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import management.device.DeviceRequester;
import management.farm.persistance.FarmEntity;
import measurement.persistence.device.MeasurementDeviceEntity;
import measurement.persistence.device.MeasurementDeviceRepository;
import measurement.persistence.record.MeasurementEntity;
import measurement.persistence.record.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.utils.Action;

@Service
public class MeasurementService {

  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementRepository measurementRepository;
  private final DeviceRequester deviceRequester;

  @Autowired
  public MeasurementService(
      MeasurementDeviceRepository measurementDeviceRepository,
      DeviceRequester deviceRequester,
      MeasurementRepository measurementRepository) {
    this.deviceRequester = deviceRequester;
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.measurementRepository = measurementRepository;
  }

  public MeasurementEntity makeMeasurements(FarmEntity farm) {
    List<MeasurementResponseMapper> responses = measurementDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .map(this::requestMeasurement).toList();
    Float measurementSum = responses.stream()
        .map(MeasurementResponseMapper::getMeasurementSum)
        .reduce((float) 0, Float::sum);
    Map<String, Float> measurements = responses.stream()
        .collect(Collectors.toMap(
            MeasurementResponseMapper::deviceId,
            MeasurementResponseMapper::getMeasurementSum));

    MeasurementEntity measurementEntity = new MeasurementEntity(farm.id(), measurementSum,
        measurements, new Date());
    measurementRepository.save(measurementEntity);
    return measurementEntity;
  }


  private MeasurementResponseMapper requestMeasurement(
      MeasurementDeviceEntity measurementDeviceEntity) {
    try {
      return deviceRequester.getData(measurementDeviceEntity, Action.READ)
          .toMapper(measurementDeviceEntity.getId());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
