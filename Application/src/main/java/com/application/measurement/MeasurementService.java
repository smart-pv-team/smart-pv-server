package com.application.measurement;

import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.management.farm.DeviceGateway;
import com.domain.ports.measurement.MeasurementDeviceRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final MeasurementDeviceRepository measurementDeviceRepository;
  private final MeasurementRepository measurementRepository;
  private final DeviceGateway deviceGateway;
  private final MeasurementStatisticsService measurementStatisticsService;

  @Autowired
  public MeasurementService(
      MeasurementDeviceRepository measurementDeviceRepository,
      DeviceGateway deviceGateway,
      MeasurementRepository measurementRepository, MeasurementStatisticsService measurementStatisticsService) {
    this.deviceGateway = deviceGateway;
    this.measurementDeviceRepository = measurementDeviceRepository;
    this.measurementRepository = measurementRepository;
    this.measurementStatisticsService = measurementStatisticsService;
  }

  public Measurement makeAndSaveMeasurement(Farm farm) {
    Measurement measurement = makeMeasurement(farm);
    measurementStatisticsService.updateMeasurementStatistics(measurement);
    measurementRepository.save(measurement);
    return measurement;
  }

  public Measurement makeMeasurement(Farm farm) {
    List<MeasurementReadAction> responses = measurementDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .map(this::requestMeasurement).toList();
    Float measurementSum = responses.stream()
        .map(MeasurementReadAction::measuredEnergy)
        .reduce((float) 0, Float::sum);
    Map<String, Float> measurements = responses.stream()
        .map((measurement -> Map.entry(measurement.measurementDevice().getId(),
            measurement.measuredEnergy())))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));

    return new Measurement(farm.id(), measurementSum, measurements, new Date());
  }


  private MeasurementReadAction requestMeasurement(MeasurementDevice measurementDevice) {
    try {
      MeasurementReadAction response = deviceGateway.requestMeasurement(measurementDevice);
      measurementDeviceRepository.setIsOn(measurementDevice, true);
      return response;
    } catch (Exception e) {
      measurementDeviceRepository.setIsOn(measurementDevice, false);
    }
    return null;
  }
}
