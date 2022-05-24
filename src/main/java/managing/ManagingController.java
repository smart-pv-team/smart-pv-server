package managing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.conf.Routing;


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

    @GetMapping(Routing.Parameters.Device.DeviceId.isOn.PATH)
    public boolean getDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
        return managementService.isDeviceOn(deviceId);
    }

    @PostMapping(Routing.Parameters.Device.DeviceId.isOn.PATH)
    public void postDeviceParametersIsOn(@PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId, @RequestBody ConsumerDeviceStatusMapper consumerDeviceStatusMapper) {
        managementService.setDeviceOn(deviceId, consumerDeviceStatusMapper.isOn);
    }

}
