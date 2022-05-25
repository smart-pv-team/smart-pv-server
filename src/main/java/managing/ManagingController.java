package managing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.conf.Routing;

import java.util.List;


@RestController
@RequestMapping(value = "/managing")
public class ManagingController {

    private final ManagementService managementService;

    @Autowired
    public ManagingController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping(Routing.Parameters.Device.DeviceId.PATH)
    public ConsumerDeviceParametersMapper getDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
        return managementService.getDeviceParameters(deviceId);
    }

    @PostMapping(Routing.Parameters.Device.DeviceId.PATH)
    public void setDeviceParameters(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId, @RequestBody ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
        managementService.setDeviceParameters(deviceId, consumerDeviceParametersMapper);
    }

    @GetMapping(Routing.Parameters.Device.DeviceId.isOn.PATH)
    public boolean getDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
        return managementService.isDeviceOn(deviceId);
    }

    @PostMapping(Routing.Parameters.Device.DeviceId.isOn.PATH)
    public void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId, @RequestBody ConsumerDeviceStatusMapper consumerDeviceStatusMapper) {
        managementService.setDeviceOn(deviceId, consumerDeviceStatusMapper.isOn);
    }

    @GetMapping("/devices")
    public List<String> getAllDevicesIds() {
        return managementService.getAllDevicesIds();
    }

}
