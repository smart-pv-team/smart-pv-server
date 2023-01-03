package com.application.farm;

import com.application.DateTimeUtils;
import com.domain.model.consumption.Consumption;
import com.domain.model.measurement.Measurement;
import com.domain.ports.consumption.ConsumptionRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FarmStatisticsService {

  private final MeasurementRepository measurementRepository;
  private final ConsumptionRepository consumptionRepository;

  public Map<Date, Float> getFarmMeasurementRange(String farmId, Date startDate, Date endDate) {
    List<Measurement> measurements = measurementRepository.findAllByFarmIdAndDateIsBetween(farmId, startDate, endDate);
    Map<Date, Float> result = new HashMap<>();
    for (Measurement measurement : measurements) {
      Date date = DateTimeUtils.withoutSeconds(measurement.getDate());
      result.put(date, result.getOrDefault(date, 0f) + measurement.getMeasurement());
    }
    return result;
  }

  public Map<Date, Integer> getFarmConsumptionRange(String farmId, Date startDate, Date endDate) {
    List<Consumption> consumptions = consumptionRepository.findByFarmIdAndDateBetween(farmId, startDate, endDate);
    return consumptions.stream()
        .map((consumption -> Map.entry(consumption.getDate(), consumption.getActiveDevicesNum())))
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
  }
}