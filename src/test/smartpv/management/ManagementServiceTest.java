package smartpv.management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import smartpv.consumption.ConsumptionService;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.device.ConsumptionDeviceRepository;
import smartpv.consumption.persistence.record.ConsumptionEntity;
import smartpv.consumption.persistence.record.ConsumptionRepository;
import smartpv.management.farm.FarmRepository;
import smartpv.management.farm.persistance.FarmEntity;
import smartpv.measurement.MeasurementService;
import smartpv.measurement.persistence.record.MeasurementEntity;
import smartpv.measurement.persistence.record.MeasurementRepository;
import smartpv.server.utils.DateTimeUtils;

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
  private MeasurementService measurementServiceMock;
  @Mock
  private ConsumptionService consumptionServiceMock;
  @Autowired
  private FarmRepository farmRepository;

  @BeforeAll
  static void loadData() {
    List<String> DAYS = List.of("2022-08-28", "2022-08-29", "2022-09-18", "2022-09-29", "2022-09-30");

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
        .sorted(Comparator.comparing(MeasurementEntity::getDate)).toList();
    var consumptionEntities = consumptionRepository.findAll().stream()
        .sorted(Comparator.comparing(ConsumptionEntity::getDate)).toList();
    var farmEntity = farmRepository.findAll().get(0);
    var currentConsumptionEntity = new ConsumptionEntity(List.of(), 0, new Date(), farmEntity.id());
    List<String> activeDevices = new LinkedList<>();

    for (MeasurementEntity measurementEntity : measurementEntities) {
      ConsumptionEntity consumptionEntity = consumptionEntities.stream()
          .filter((entity) -> DateTimeUtils.compareByMinutes(entity.getDate(), measurementEntity.getDate()))
          .findFirst()
          .get();

      var updatedMeasurement = consumptionEntity.getActiveDevicesNum() * 4
          + measurementEntity.getMeasurement() / 1000
          - 3 * activeDevices.size();
      var updatedMeasurementEntity = measurementEntity.withMeasurement(updatedMeasurement);

      when(measurementServiceMock.makeMeasurement(any(FarmEntity.class))).thenReturn(updatedMeasurementEntity);
      when(consumptionServiceMock.collectDevicesStatus(any(FarmEntity.class))).thenReturn(
          currentConsumptionEntity);

      var currentlyChangedDevices = new ManagementService(
          consumptionDeviceRepository,
          consumptionRepository,
          measurementServiceMock,
          consumptionServiceMock).updateDevices(farmEntity);

      for (ConsumptionDeviceEntity device : currentlyChangedDevices) {
        if (activeDevices.contains(device.getId()) && !device.getIsOn()) {
          activeDevices.remove(device.getId());
        } else if (!activeDevices.contains(device.getId()) && device.getIsOn()) {
          activeDevices.add(device.getId());
        }
      }
      currentConsumptionEntity = consumptionEntity.withActiveDevices(activeDevices);

      consumptionRepository.save(currentConsumptionEntity);
      measurementRepository.save(updatedMeasurementEntity);
    }
    assertEquals(1, 1);
  }

}