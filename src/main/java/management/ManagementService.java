package management;

import consumption.ConsumptionService;
import consumption.ControlParameters;
import consumption.persistence.device.ConsumptionDeviceEntity;
import consumption.persistence.device.ConsumptionDeviceRepository;
import consumption.persistence.record.ConsumptionEntity;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import management.farm.persistance.FarmEntity;
import measurement.MeasurementService;
import measurement.persistence.record.MeasurementEntity;
import measurement.persistence.record.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final MeasurementRepository measurementRepository;
  private final MeasurementService measurementService;
  private final ConsumptionService consumptionService;

  @Autowired
  public ManagementService(ConsumptionDeviceRepository consumptionDeviceRepository,
      MeasurementRepository measurementRepository, MeasurementService measurementService,
      ConsumptionService consumptionService) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.measurementRepository = measurementRepository;
    this.measurementService = measurementService;
    this.consumptionService = consumptionService;
  }


  public String updateDevicesStatus(FarmEntity farm) {
    StringBuilder builder = new StringBuilder();

    MeasurementEntity measurement = measurementService.makeMeasurement(farm);
    ConsumptionEntity consumption = consumptionService.collectDevicesStatus(farm);
    List<ConsumptionDeviceEntity> activeDevices = consumptionDeviceRepository
        .findAllByFarmIdAndIdIsIn(farm.id(), consumption.getActiveDevicesIds());

    List<ConsumptionDeviceEntity> allDevices = consumptionDeviceRepository.findAllByFarmId(
        farm.id());
    allDevices.sort(Comparator.comparing(o -> -o.getControlParameters().priority()));

    checkLock(activeDevices, allDevices);

    Float measuredEnergy = measurement.getMeasurement();
    Float consumedEnergy = activeDevices
        .stream()
        .map(ConsumptionDeviceEntity::getControlParameters)
        .map(ControlParameters::powerConsumption)
        .reduce(0f, Float::sum);
    Float availableEnergy = measuredEnergy + consumedEnergy;

    List<ConsumptionDeviceEntity> devicesToTurnOn = new LinkedList<>();
    for (ConsumptionDeviceEntity device : allDevices) {
      if (availableEnergy - device.getControlParameters().powerConsumption() > 0 &&
          !device.getControlParameters().lock().isLocked()) {
        devicesToTurnOn.add(device);
        availableEnergy -= device.getControlParameters().powerConsumption();
      }
    }
    List<ConsumptionDeviceEntity> devicesToTurnOff = activeDevices.stream()
        .filter((device) -> !devicesToTurnOn.contains(device)).toList();

    devicesToTurnOn.forEach((device) -> consumptionService.turnDevice(device, true));
    devicesToTurnOff.forEach((device) -> consumptionService.turnDevice(device, false));

    return builder
        .append("Devices to turnOff: ").append(devicesToTurnOff)
        .append("Devices to turnOn: ").append(devicesToTurnOn).toString();
  }

  private void checkLock(List<ConsumptionDeviceEntity> activeDevices,
      List<ConsumptionDeviceEntity> allDevices) {
    allDevices.forEach((device) -> {
      if (!device.getControlParameters().lock().isLocked()) {
        boolean isDeviceOnRequestResponse = activeDevices.contains(device);
        if ((device.getIsOn() && !isDeviceOnRequestResponse) ||
            (!device.getIsOn() && isDeviceOnRequestResponse)) {
          consumptionDeviceRepository.setLock(device.getId(), true);
          consumptionDeviceRepository.setDeviceOn(device.getId(), isDeviceOnRequestResponse);
        }
      } else {
        if (device.getControlParameters().lock().date().after(new Date())) {
          consumptionDeviceRepository.setLock(device.getId(), false);
          consumptionDeviceRepository.setDeviceOn(device.getId(), false);
        }
      }
    });
  }
}
