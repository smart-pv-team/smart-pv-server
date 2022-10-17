package smartpv.measurement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smartpv.management.device.DeviceRequester;
import smartpv.measurement.persistence.device.MeasurementDeviceRepository;
import smartpv.measurement.persistence.record.MeasurementEntity;
import smartpv.measurement.persistence.record.MeasurementRepository;

@Service
@AllArgsConstructor
public class MeasurementStatisticsService {

  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementRepository measurementRepository;
  private final DeviceRequester deviceRequester;

  public Long updateMeasurement(MeasurementEntity measurementEntity) {
    measurementEntity.getMeasurements().forEach((deviceId, measurement) -> {
      MeasurementEntity recentMeasurement = measurementRepository.findTopByDateAndIdIsNot(measurementEntity.getId())
          .get();
    });
  }
}
