package measurement;

import java.util.List;
import management.device.Device;
import measurement.persistence.device.MeasurementDeviceEntity;
import measurement.persistence.device.MeasurementDeviceRepository;
import measurement.persistence.record.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import server.conf.Routing;

@RestController
class MeasurementController {

  private final MeasurementRepository measurementRepository;
  private final MeasurementDeviceRepository measurementDeviceRepository;

  @Autowired
  MeasurementController(MeasurementRepository measurementRepository,
      MeasurementDeviceRepository measurementDeviceRepository) {
    this.measurementRepository = measurementRepository;
    this.measurementDeviceRepository = measurementDeviceRepository;
  }

  @GetMapping(Routing.Measurement.Devices.PATH)
  List<String> getDevices() {
    return measurementDeviceRepository.findAll().stream().map(Device::getId).toList();
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.PATH)
  ResponseEntity<MeasurementDeviceEntity> getDevice(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementDeviceRepository
        .getFirstById(deviceId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Measurement.Devices.DeviceId.Parameters.Name.PATH)
  ResponseEntity<String> getDeviceName(
      @PathVariable(Routing.DEVICE_ID_VARIABLE) String deviceId) {
    return measurementDeviceRepository
        .getFirstById(deviceId)
        .map(Device::getName)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}
