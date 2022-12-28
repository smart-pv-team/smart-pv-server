package com.application.consumption;


import com.domain.model.consumption.Consumption;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.consumption.ConsumptionRepository;
import com.domain.ports.management.farm.CounterGateway;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConsumptionStatisticsService {

  private final ConsumptionRepository consumptionRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final CounterGateway counter;

  public void updateConsumptionStatistics(Consumption consumption) {
    Consumption last = consumptionRepository.findLast(consumption.getFarmId()).orElseThrow();
    consumption.getActiveDevicesIds().forEach((id) -> {
      if (last.getActiveDevicesIds().contains(id)) {
        Long diff = counter.countDuration(Map.of(
            last.getDate(), true,
            consumption.getDate(), true
        )).longValue();
        consumptionDeviceRepository.saveConsumptionStatistic(id, diff);
      }
    });
  }

  public Double getPeriodFarmWorkingHoursStatisticsSum(String farmId, Date startDate, Date endDate) {
    return counter.countPeriodFarmWorkingHoursStatisticsSum(farmId, startDate, endDate);
  }
}
