package management;

import consumption.persistence.ConsumerDeviceRepository;
import management.persistence.FarmEntity;
import measurement.persistence.sum.MeasurementSumMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {

  private final ConsumerDeviceRepository consumerDeviceRepository;
  private final MeasurementSumMongoRepository measurementSumMongoRepository;

  @Autowired
  public ManagementService(ConsumerDeviceRepository consumerDeviceRepository,
      MeasurementSumMongoRepository measurementSumMongoRepository) {
    this.consumerDeviceRepository = consumerDeviceRepository;
    this.measurementSumMongoRepository = measurementSumMongoRepository;
  }


  public String updateDevicesStatus(FarmEntity farm) {
    StringBuilder builder = new StringBuilder();
    measurementSumMongoRepository.findTopByFarmIdOrderByDateDesc(farm.id())
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
