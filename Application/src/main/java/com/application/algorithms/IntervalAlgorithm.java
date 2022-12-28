package com.application.algorithms;

import com.application.DateTimeUtils;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.algorithm.Interval;
import com.domain.model.management.algorithm.Rule;
import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.management.farm.algorithm.Algorithm;
import com.domain.ports.management.farm.algorithm.IntervalRepository;
import com.domain.ports.management.farm.algorithm.RuleRepository;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@AllArgsConstructor
@Component
public class IntervalAlgorithm implements Algorithm {

  private final IntervalRepository intervalRepository;
  private final RuleRepository ruleRepository;

  @Override
  public List<ConsumptionDevice> updateDevicesStatus(Measurement measuredEnergy,
      List<ConsumptionDevice> devices, Farm farm) {

    List<Interval> intervals = intervalRepository.getFarmIntervals(farm.id());

    Float measurement = measuredEnergy.getMeasurement();
    Interval interval = intervals.stream()
        .filter((i) -> i.getLowerBound() < measurement && i.getUpperBound() > measurement)
        .findFirst()
        .orElseThrow(() -> new NotFoundException("Interval not found"));

    List<Rule> rules = ruleRepository.getIntervalRules(interval.getId());

    List<ConsumptionDevice> devicesNotInRules = devices.stream()
        .filter((device) -> !device.getControlParameters().lock().isLocked())
        .filter(Device::getIsOn)
        .filter((device) -> !rules.stream().map(Rule::getDeviceId).toList().contains(device.getId()))
        .filter((device) -> device.getControlParameters().lastStatusChange()
            .before(DateTimeUtils.subtractMinutes(measuredEnergy.getDate(), farm.minutesBetweenDeviceStatusSwitch())))
        .peek((device) -> {
          device.setControlParameters(device.getControlParameters().withLastStatusChange(measuredEnergy.getDate()));
          device.setIsOn(false);
        }).toList();
    List<ConsumptionDevice> devicesInRules = rules.stream().map((rule) -> {
      ConsumptionDevice consumptionDevice = devices.stream()
          .filter(d -> d.getId().equals(rule.getDeviceId()))
          .findFirst().orElseThrow();
      consumptionDevice.setIsOn(true);
      consumptionDevice.setControlParameters(
          consumptionDevice.getControlParameters().withLastStatusChange(measuredEnergy.getDate()));
      consumptionDevice.setControlParameters(
          consumptionDevice.getControlParameters().withStatusChange(rule.getAction()));
      return consumptionDevice;
    }).toList();

    List<ConsumptionDevice> devicesToChange = Stream.concat(devicesNotInRules.stream(), devicesInRules.stream())
        .toList();
    devicesToChange.forEach(
        (deviceToChange) -> devices.set(
            devices
                .stream()
                .filter((device) -> device.getId().equals(deviceToChange.getId()))
                .findFirst()
                .map((devices::indexOf))
                .orElse(0),
            deviceToChange
        )
    );
    return devices;
  }
}
