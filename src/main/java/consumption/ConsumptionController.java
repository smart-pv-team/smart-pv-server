package consumption;

import consumption.persistence.device.ConsumptionDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;
import server.conf.Routing.Consumption.Parameters.DeviceId.IsOn;


@RestController
public class ConsumptionController {

  private final ConsumptionDeviceRepository consumptionDeviceRepository;

  @Autowired
  public ConsumptionController(ConsumptionDeviceRepository consumptionDeviceRepository) {
    this.consumptionDeviceRepository = consumptionDeviceRepository;
  }

  @GetMapping(Routing.Consumption.Parameters.DeviceId.PATH)
  public ControlParametersMapper getDeviceParameters(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    //TODO: optional handling
    return ControlParametersMapper.ofControlParameters(
        consumptionDeviceRepository.getDeviceParameters(deviceId).get());
  }

  @PostMapping(Routing.Consumption.Parameters.DeviceId.PATH)
  public void setDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ControlParametersMapper controlParameters) {
    consumptionDeviceRepository.setDeviceParameters(deviceId,
        ControlParametersMapper.toControlParameters(controlParameters));
  }

  @GetMapping(Routing.Consumption.Parameters.DeviceId.IsOn.PATH)
  public boolean getDeviceParametersIsOn(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return consumptionDeviceRepository.isDeviceOn(deviceId).get();
  }

  @PostMapping(IsOn.PATH)
  public void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId,
      @RequestBody ConsumptionMapper consumptionMapper) {
    consumptionDeviceRepository.setDeviceOn(deviceId, consumptionMapper.isOn);
  }
}
