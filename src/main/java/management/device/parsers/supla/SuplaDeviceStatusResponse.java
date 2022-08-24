package management.device.parsers.supla;

import consumption.dto.ConsumptionResponseMapper;
import management.device.parsers.Response;

public record SuplaDeviceStatusResponse(Boolean connected, Boolean on,
                                        Boolean currentOverload) implements Response {

  @Override
  public ConsumptionResponseMapper toMapper(String deviceId) {
    return new ConsumptionResponseMapper(connected && on, deviceId);
  }
}