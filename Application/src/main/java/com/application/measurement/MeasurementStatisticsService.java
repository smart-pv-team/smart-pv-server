package com.application.measurement;

import com.domain.model.measurement.Measurement;
import com.domain.ports.farm.CounterGateway;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementStatisticsService {

  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementRepository measurementRepository;
  private final CounterGateway counter;

  @Autowired
  public MeasurementStatisticsService(MeasurementDeviceRepository measurementDeviceRepository,
      MeasurementRepository measurementRepository, CounterGateway counter) {
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.measurementRepository = measurementRepository;
    this.counter = counter;
  }

  public void updateMeasurementStatistics(Measurement measurement) {
    Measurement recentMeasurement = measurementRepository
        .findTopByDate().get();

    measurement.getMeasurements().forEach((deviceId, measurement1) -> {
      Map<Date, Double> measurements = Map.of(
          recentMeasurement.getDate(), recentMeasurement.getMeasurements().get(deviceId).doubleValue(),
          measurement.getDate(), measurement1.doubleValue()
      );
      Double difference = counter.countSimpson(measurements);
      measurementDeviceRepository.saveMeasurementStatistics(deviceId, difference.longValue());
    });
  }
}