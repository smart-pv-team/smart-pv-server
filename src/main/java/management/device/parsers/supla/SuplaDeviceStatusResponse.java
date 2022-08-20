package management.device.parsers.supla;

import consumption.ConsumerDeviceStatusResponseMapper;
import management.device.parsers.Response;

public record SuplaDeviceStatusResponse(Boolean connected, Boolean on,
                                        Boolean currentOverload) implements Response {

  @Override
  public ConsumerDeviceStatusResponseMapper toMapper(String deviceId) {
    return new ConsumerDeviceStatusResponseMapper(connected && on, deviceId);
  }
}