package consumption;

import consumption.dto.ConsumptionIsOnDto;
import consumption.persistence.device.ConsumptionDeviceEntity;
import consumption.persistence.device.ConsumptionDeviceRepository;
import java.util.List;
import management.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;
import server.conf.Routing.Consumption.Devices;


@RestController
class ConsumptionController {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;

  @Autowired
  ConsumptionController(ConsumptionDeviceRepository consumptionDeviceRepository) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
  }

  @GetMapping(Devices.PATH)
  List<String> getDevices() {
    return consumptionDeviceRepository
        .findAll()
        .stream()
        .map(Device::getId)
        .toList();
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.PATH)
  ResponseEntity<ConsumptionDeviceEntity> getDevice(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .findById(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Parameters.PATH)
  ResponseEntity<ControlParameters> getDeviceParameters(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .getDeviceParameters(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(Routing.Consumption.Devices.DeviceId.Parameters.PATH)
  void setDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ControlParameters controlParameters) {
    consumptionDeviceRepository.setDeviceParameters(deviceId, controlParameters);
  }

  @GetMapping(Routing.Consumption.Devices.DeviceId.Parameters.IsOn.PATH)
  ResponseEntity<Boolean> getDeviceParametersIsOn(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository
        .isDeviceOn(deviceId).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping(Routing.Consumption.Devices.DeviceId.Parameters.IsOn.PATH)
  void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ConsumptionIsOnDto consumptionIsOnDto) {
    consumptionDeviceRepository.setDeviceOn(deviceId, consumptionIsOnDto.isOn());
  }
}
