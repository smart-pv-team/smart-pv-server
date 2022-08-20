package consumption;

import consumption.persistence.device.ConsumerDeviceEntity;
import consumption.persistence.device.ConsumerDeviceRepository;
import consumption.persistence.status.ConsumerDeviceStatusEntity;
import consumption.persistence.status.ConsumerDeviceStatusRepository;
import java.util.Date;
import java.util.List;
import management.device.DeviceRequester;
import management.farm.FarmEntity;
import org.springframework.stereotype.Service;
import server.utils.Action;

@Service
public class ConsumerDeviceService {

  private final ConsumerDeviceStatusRepository consumerDeviceStatusRepository;
  private final ConsumerDeviceRepository consumerDeviceRepository;
  private final DeviceRequester deviceRequester;

  public ConsumerDeviceService(ConsumerDeviceStatusRepository consumerDeviceStatusRepository,
      ConsumerDeviceRepository consumerDeviceRepository, DeviceRequester deviceRequester) {
    this.consumerDeviceStatusRepository = consumerDeviceStatusRepository;
    this.consumerDeviceRepository = consumerDeviceRepository;
    this.deviceRequester = deviceRequester;
  }

  public ConsumerDeviceStatusEntity collectDevicesStatus(FarmEntity farm) {
    List<String> activeDevices = consumerDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .map(this::requestDevicesStatus)
        .filter(ConsumerDeviceStatusResponseMapper::isOn)
        .map(ConsumerDeviceStatusResponseMapper::deviceId)
        .toList();

    ConsumerDeviceStatusEntity consumerDeviceStatusEntity = new ConsumerDeviceStatusEntity(
        activeDevices, activeDevices.size(), new Date());

    consumerDeviceStatusRepository.save(consumerDeviceStatusEntity);
    return consumerDeviceStatusEntity;
  }


  private ConsumerDeviceStatusResponseMapper requestDevicesStatus(
      ConsumerDeviceEntity consumerDeviceEntity) {
    try {
      return deviceRequester.getData(
          consumerDeviceEntity, Action.READ).toMapper(consumerDeviceEntity.getId());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
