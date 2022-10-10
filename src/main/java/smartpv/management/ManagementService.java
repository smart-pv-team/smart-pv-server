package smartpv.management;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartpv.consumption.ConsumptionService;
import smartpv.consumption.persistence.device.ConsumptionDeviceEntity;
import smartpv.consumption.persistence.device.ConsumptionDeviceRepository;
import smartpv.consumption.persistence.record.ConsumptionRepository;
import smartpv.management.algorithms.StringToAlgorithm;
import smartpv.management.farm.persistance.FarmEntity;
import smartpv.measurement.MeasurementService;
import smartpv.measurement.persistence.record.MeasurementEntity;
import smartpv.server.utils.DateTimeUtils;

@Service
public class ManagementService {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final ConsumptionRepository consumptionRepository;
  private final MeasurementService measurementService;
  private final ConsumptionService consumptionService;

  @Autowired
  public ManagementService(ConsumptionDeviceRepository consumptionDeviceRepository,
      ConsumptionRepository consumptionRepository, MeasurementService measurementService,
      ConsumptionService consumptionService) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.consumptionRepository = consumptionRepository;
    this.measurementService = measurementService;
    this.consumptionService = consumptionService;
  }

  public List<ConsumptionDeviceEntity> updateDevices(FarmEntity farm) {
    MeasurementEntity measuredEnergy = measurementService.makeMeasurement(farm);
    List<ConsumptionDeviceEntity> activeDevicesRequestResponse = consumptionDeviceRepository
        .findAllByFarmIdAndIdIsIn(farm.id(), consumptionService.collectDevicesStatus(farm).getActiveDevicesIds());

    List<ConsumptionDeviceEntity> allDevices = consumptionDeviceRepository.findAllByFarmId(farm.id());
    List<ConsumptionDeviceEntity> allDevicesWithUpdatedLock = updateDevicesLock(activeDevicesRequestResponse,
        allDevices);
    List<ConsumptionDeviceEntity> allDevicesWithUpdatedLockAndStatus = StringToAlgorithm.stringToAlgorithm(
            farm.algorithmType())
        .updateDevicesStatus(
            measuredEnergy,
            allDevicesWithUpdatedLock,
            farm);

    List<ConsumptionDeviceEntity> updatedDevices = allDevicesWithUpdatedLockAndStatus.stream()
        .filter((device) -> {
          boolean isActive = activeDevicesRequestResponse.stream().map(ConsumptionDeviceEntity::getId).toList()
              .contains(device.getId());
          return device.getIsOn() && !isActive
              || !device.getIsOn() && isActive;
        }).toList();
    consumptionDeviceRepository.saveAll(allDevicesWithUpdatedLockAndStatus);
    updatedDevices.forEach((device) -> consumptionService.turnDeviceRequest(device, device.getIsOn()));

    return updatedDevices;
  }

  private List<ConsumptionDeviceEntity> updateDevicesLock(List<ConsumptionDeviceEntity> activeDevices,
      List<ConsumptionDeviceEntity> allDevices) {
    allDevices.forEach((device) -> {
      boolean isDeviceOnRequestResponse = activeDevices.contains(device);
      if (!device.getControlParameters().lock().isLocked()) {
        if ((device.getIsOn() && !isDeviceOnRequestResponse)
            || (!device.getIsOn() && isDeviceOnRequestResponse)) {
          device.setControlParameters(device.getControlParameters().withLock(true));
        }
      } else {
        if (device.getControlParameters().lock().date().after(DateTimeUtils.getNow())) {
          device.setControlParameters(device.getControlParameters().withLock(false));
        }
      }
      device.setIsOn(isDeviceOnRequestResponse);
    });
    return allDevices;
  }
}
