package com.domain.ports.management.farm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CounterGateway {

  Double countAverage(List<Double> data);

  Double countDuration(Map<Date, Boolean> data);

  Double countSimpson(Map<Date, Double> data);

  Double countPeriodFarmWorkingHoursStatisticsSum(String farmId, Date startDate, Date endDate);

  Double countPeriodDeviceWorkingHoursStatistics(String deviceId, Date startDate, Date endDate);

  Double countPeriodFarmEnergyStatisticsSum(String farmId, Date startDate, Date endDate);

  Double countPeriodDeviceEnergyStatistics(String deviceId, Date startDate, Date endDate);
}
