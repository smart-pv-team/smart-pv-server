package management;

import consumption.persistence.device.ConsumerDeviceRepository;
import management.farm.FarmEntity;
import measurement.persistence.record.MeasurementMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

  private final ConsumerDeviceRepository consumerDeviceRepository;
  private final MeasurementMongoRepository measurementMongoRepository;

  @Autowired
  public ManagementService(ConsumerDeviceRepository consumerDeviceRepository,
      MeasurementMongoRepository measurementMongoRepository) {
    this.consumerDeviceRepository = consumerDeviceRepository;
    this.measurementMongoRepository = measurementMongoRepository;
  }


  public String updateDevicesStatus(FarmEntity farm) {
    StringBuilder builder = new StringBuilder();
    measurementMongoRepository.findTopByFarmIdOrderByDateDesc(farm.id())
        .ifPresentOrElse((record) -> {
              Float availableEnergy = record.getMeasurement();
              builder.append("[AVAILABLE ENERGY] ").append(availableEnergy);

              if (availableEnergy >= 0) {
                consumerDeviceRepository.findHighestPriorityOffDevice(farm).ifPresent((device) -> {
                  if (availableEnergy - device.getControlParameters().getPowerConsumption() >= 0) {
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
            },
            () -> builder.append("No devices to manage")
        );
    return builder.toString();
  }

}
