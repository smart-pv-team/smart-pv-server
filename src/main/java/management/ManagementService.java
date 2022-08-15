package management;

import consumption.persistence.ConsumerDeviceRepository;
import management.persistence.FarmEntity;
import measurement.persistence.MeasurementSumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

  private final ConsumerDeviceRepository consumerDeviceRepository;
  private final MeasurementSumRepository measurementSumRepository;

  @Autowired
  public ManagementService(ConsumerDeviceRepository consumerDeviceRepository,
      MeasurementSumRepository measurementSumRepository) {
    this.consumerDeviceRepository = consumerDeviceRepository;
    this.measurementSumRepository = measurementSumRepository;
  }


  public String updateDevicesStatus(FarmEntity farm) {
    StringBuilder builder = new StringBuilder();
    Float availableEnergy = measurementSumRepository.findTopByFarmIdOrderByDateDesc(farm.id())
        .getMeasurement();

    builder.append("[AVAILABLE ENERGY] ").append(availableEnergy);

    if (availableEnergy >= 0) {
      consumerDeviceRepository.findHighestPriorityOffDevice(farm).ifPresent((device) -> {
        if (availableEnergy - device.getPowerConsumption() >= 0) {
          consumerDeviceRepository.setDeviceOn(device.getId(), true);
          builder.append("Turned on device: ").append(device.getId());
        }
      });
    } else {
      consumerDeviceRepository.findLowestPriorityOnDevice(farm).ifPresent((device) -> {
        consumerDeviceRepository.setDeviceOn(device.getId(), false);
        builder.append("Turned off device: ").append(device.getId());
      });
    }
    return builder.toString();
  }

}
