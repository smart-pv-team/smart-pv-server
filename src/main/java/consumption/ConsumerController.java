package consumption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;
import server.conf.Routing.Consumption.Parameters.DeviceId.IsOn;


@RestController
public class ConsumerController {

  private final ConsumerDeviceRepository consumerDeviceRepository;

  @Autowired
  public ConsumerController(ConsumerDeviceRepository consumerDeviceRepository) {
    this.consumerDeviceRepository = consumerDeviceRepository;
  }

  @GetMapping(Routing.Consumption.Parameters.DeviceId.PATH)
  public ConsumerDeviceParametersMapper getDeviceParameters(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumerDeviceRepository.getDeviceParameters(deviceId);
  }

  @PostMapping(Routing.Consumption.Parameters.DeviceId.PATH)
  public void setDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
    consumerDeviceRepository.setDeviceParameters(deviceId, consumerDeviceParametersMapper);
  }

  @GetMapping(Routing.Consumption.Parameters.DeviceId.IsOn.PATH)
  public boolean getDeviceParametersIsOn(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumerDeviceRepository.isDeviceOn(deviceId);
  }

  @PostMapping(IsOn.PATH)
  public void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ConsumerDeviceStatusMapper consumerDeviceStatusMapper) {
    consumerDeviceRepository.setDeviceOn(deviceId, consumerDeviceStatusMapper.isOn);
  }
}
