package com.adapters.inbound.http.management;

import com.adapters.inbound.http.Routing;
import com.adapters.outbound.http.devices.ResponseTypeAdapter;
import com.application.farm.FarmService;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.algorithm.Interval;
import com.domain.model.management.algorithm.Rule;
import com.domain.model.management.farm.AlgorithmType;
import com.domain.model.management.farm.DeviceModel;
import com.domain.model.management.farm.Farm;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.management.farm.FarmRepository;
import com.domain.ports.management.farm.algorithm.IntervalRepository;
import com.domain.ports.management.farm.algorithm.RuleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FarmController {

  private final FarmRepository farmRepository;
  private final FarmService farmService;
  private final RuleRepository ruleRepository;
  private final IntervalRepository intervalRepository;


  @GetMapping(Routing.Management.Farms.PATH)
  List<String> getFarms() {
    return farmRepository.findAll().stream().map(Farm::id).collect(Collectors.toList());
  }

  @GetMapping(Routing.Management.Farms.FarmId.PATH)
  ResponseEntity<Farm> getFarm(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository
        .getById(farmId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Management.Farms.FarmId.Measurement.Devices.PATH)
  List<MeasurementDevice> getFarmMeasurementDevices(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository.getAllFarmMeasurementDevices(farmId);
  }

  @GetMapping(Routing.Management.Farms.FarmId.Consumption.Devices.PATH)
  List<ConsumptionDevice> getFarmConsumptionDevices(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository.getAllFarmConsumptionDevices(farmId);
  }

  @GetMapping(Routing.Management.Farms.FarmId.Parameters.Name.PATH)
  ResponseEntity<String> getFarmName(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository
        .getById(farmId)
        .map(Farm::name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PatchMapping(Routing.Management.Farms.PATH)
  ResponseEntity<String> saveFarm(@RequestBody Farm farm) {
    return ResponseEntity.ok(farmRepository.save(farm));
  }

  @PatchMapping(Routing.Management.Farms.FarmId.Parameters.Algorithm.PATH)
  void setFarmAlgorithm(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId,
      @RequestBody AlgorithmTypeDto algorithmTypeDto) {
    farmService.changeAlgorithmType(farmId, AlgorithmType.valueOf(algorithmTypeDto.algorithmType()));
  }

  @PatchMapping(Routing.Management.Farms.FarmId.Parameters.Running.PATH)
  void setFarmRunning(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId, @RequestBody Boolean running) {
    farmRepository.setFarmRunning(farmId, running);
  }


  @PatchMapping(Routing.Management.Algorithm.Interval.PATH)
  void saveInterval(@RequestBody Interval interval) {
    intervalRepository.save(interval);
  }


  @PatchMapping(Routing.Management.Algorithm.Rule.PATH)
  void saveRule(@RequestBody Rule rule) {
    ruleRepository.save(rule);
  }

  @DeleteMapping(Routing.Management.Algorithm.Interval.IntervalId.PATH)
  void deleteInterval(@PathVariable(Routing.INTERVAL_ID_VARIABLE) String intervalId) {
    intervalRepository.delete(intervalId);
  }

  @DeleteMapping(Routing.Management.Algorithm.Rule.RuleId.PATH)
  void deleteRule(@PathVariable(Routing.RULE_ID_VARIABLE) String ruleId) {
    ruleRepository.delete(ruleId);
  }

  @GetMapping(Routing.Management.Algorithm.Interval.IntervalId.Rule.PATH)
  List<Rule> getIntervalRules(@PathVariable(Routing.INTERVAL_ID_VARIABLE) String intervalId) {
    return ruleRepository.getIntervalRules(intervalId);
  }

  @GetMapping(Routing.Management.Farms.FarmId.Algorithm.Interval.PATH)
  List<Interval> getFarmIntervals(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return intervalRepository.getFarmIntervals(farmId);
  }

  @GetMapping(Routing.Management.DevicesModel.PATH)
  List<DeviceModel> getDevicesModel() {
    return List.of(DeviceModel.values());
  }


  @GetMapping(Routing.Management.ResponseOptions.PATH)
  List<ResponseTypeAdapter> getResponseOptions() {
    return List.of(ResponseTypeAdapter.values());
  }

}
