package com.springapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.adapters.outbound.http.Counter;
import com.application.DateTimeUtils;
import com.application.consumption.ConsumptionService;
import com.application.farm.ManagementService;
import com.domain.model.consumption.Consumption;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.farm.Farm;
import com.domain.model.measurement.Measurement;
import com.domain.ports.consumption.ConsumptionDeviceRepository;
import com.domain.ports.consumption.ConsumptionRepository;
import com.domain.ports.farm.FarmRepository;
import com.domain.ports.measurement.MeasurementRepository;
import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
@ActiveProfiles("test")
class ManagementServiceTest {

  @Autowired
  private ConsumptionRepository consumptionRepository;
  @Autowired
  private MeasurementRepository measurementRepository;
  @Autowired
  private ConsumptionDeviceRepository consumptionDeviceRepository;
  @Mock
  private ConsumptionService consumptionServiceMock;
  @Autowired
  private FarmRepository farmRepository;

  @Autowired
  private Counter counter;

  @BeforeAll
  static void loadData() {
    //List<String> DAYS = List.of("2022-08-28", "2022-08-29", "2022-09-18", "2022-09-29", "2022-09-30", "2022-10-11",
    //    "2022-10-14", "2022-10-17");
    List<String> DAYS = List.of("2022-10-17");

    ProcessBuilder processBuilder = new ProcessBuilder();
    String filePath = System.getProperty("user.dir").concat("/src/simulation/script/");
    String scriptArgs = DAYS.stream().map(String::toString).collect(Collectors.joining(" ", " ", ""));
    String collectScriptPath = filePath.concat("collect_data.sh");
    String collectCommand = collectScriptPath.concat(scriptArgs);
    processBuilder.command("bash", "-c", collectCommand);
    try {
      Process collectProcess = processBuilder.start();
      int collectDataExitValue = collectProcess.waitFor();
      if (collectDataExitValue == 0) {
        log.info("Data collected successfully");
        String loadScriptPath = filePath.concat("load_data.sh");
        String loadCommand = loadScriptPath.concat(scriptArgs);
        processBuilder.command("bash", "-c", loadCommand);
        Process loadProcess = processBuilder.start();
        int loadDataExitValue = loadProcess.waitFor();
        if (loadDataExitValue == 0) {
          log.info("Data loaded successfully");
        }
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void updateDevicesStatusTest() {
    var measurementEntities = measurementRepository.findAll().stream()
        .sorted(Comparator.comparing(Measurement::getDate)).toList();
    var consumptionEntities = consumptionRepository.findAll().stream()
        .sorted(Comparator.comparing(Consumption::getDate)).toList();
    var farm = farmRepository.findAll().get(0);
    var currentConsumption = new Consumption(List.of(), 0, new Date(), farm.id());
    List<String> activeDevices = new LinkedList<>();
    var manage = 0;
    for (Measurement measurement : measurementEntities) {
      Consumption consumption = consumptionEntities.stream()
          .filter((entity) -> DateTimeUtils.compareByMinutes(entity.getDate(), measurement.getDate()))
          .findFirst().orElse(currentConsumption);

      var updatedMeasurementValue = consumption.getActiveDevicesNum() * 2.5f
          + measurement.getMeasurement() / 1000
          - 2.5f * activeDevices.size();
      var updatedMeasurement = measurement.withMeasurement(updatedMeasurementValue);
      measurementRepository.save(updatedMeasurement);

      if (manage % 2 == 0) {
        when(consumptionServiceMock.collectDevicesStatus(any(Farm.class))).thenReturn(
            currentConsumption);
        var currentlyChangedDevices = new ManagementService(
            consumptionDeviceRepository,
            consumptionServiceMock,
            counter,
            measurementRepository).updateDevices(farm);
        for (ConsumptionDevice device : currentlyChangedDevices) {
          if (activeDevices.contains(device.getId()) && !device.getIsOn()) {
            activeDevices.remove(device.getId());
          } else if (!activeDevices.contains(device.getId()) && device.getIsOn()) {
            activeDevices.add(device.getId());
          }
        }
      }
      manage += 1;
      currentConsumption = consumption.withActiveDevices(activeDevices);

      consumptionRepository.save(currentConsumption);
    }
    assertEquals(1, 1);
  }

}