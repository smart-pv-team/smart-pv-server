package consumption;

import consumption.dto.ConsumptionResponseMapper;
import consumption.dto.ConsumptionTurnOnOffResponseMapper;
import consumption.persistence.device.ConsumptionDeviceEntity;
import consumption.persistence.device.ConsumptionDeviceRepository;
import consumption.persistence.record.ConsumptionEntity;
import consumption.persistence.record.ConsumptionRepository;
import java.util.Date;
import java.util.List;
import management.device.DeviceRequester;
import management.farm.persistance.FarmEntity;
import org.springframework.stereotype.Service;
import server.utils.Action;

@Service
public class ConsumptionService {

  private final ConsumptionRepository consumptionRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;
  private final DeviceRequester deviceRequester;

  public ConsumptionService(ConsumptionRepository consumptionRepository,
      ConsumptionDeviceRepository consumptionDeviceRepository, DeviceRequester deviceRequester) {
    this.consumptionRepository = consumptionRepository;
    this.consumptionDeviceRepository = consumptionDeviceRepository;
    this.deviceRequester = deviceRequester;
  }

  public ConsumptionTurnOnOffResponseMapper turnDevice(
      ConsumptionDeviceEntity consumptionDeviceEntity, Boolean status) {
    try {
      ConsumptionTurnOnOffResponseMapper result = deviceRequester.request(consumptionDeviceEntity,
          status ? Action.TURN_ON : Action.TURN_OFF).toMapper(consumptionDeviceEntity.getId());
      consumptionDeviceRepository.setDeviceOn(consumptionDeviceEntity.getId(), status);
      return result;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public ConsumptionEntity collectAndSaveDevicesStatus(FarmEntity farm) {
    ConsumptionEntity consumptionEntity = collectDevicesStatus(farm);
    consumptionRepository.save(consumptionEntity);
    return consumptionEntity;
  }

  public ConsumptionEntity collectDevicesStatus(FarmEntity farm) {
    List<String> activeDevices = consumptionDeviceRepository
        .findAllByFarmId(farm.id())
        .stream()
        .map(this::requestDevicesStatus)
        .filter(ConsumptionResponseMapper::isOn)
        .map(ConsumptionResponseMapper::deviceId)
        .toList();

    return new ConsumptionEntity(
        activeDevices, activeDevices.size(), new Date());
  }


  private ConsumptionResponseMapper requestDevicesStatus(
      ConsumptionDeviceEntity consumptionDeviceEntity) {
    try {
      return deviceRequester.request(
          consumptionDeviceEntity, Action.READ).toMapper(consumptionDeviceEntity.getId());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
