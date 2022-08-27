package management.device.parsers.supla;

import consumption.dto.ConsumptionTurnOnOffResponseMapper;
import management.device.parsers.Response;

public record SuplaTurnOnOffResponse(Boolean success) implements Response {

  @Override
  public ConsumptionTurnOnOffResponseMapper toMapper(String deviceId) {
    return new ConsumptionTurnOnOffResponseMapper(deviceId, success);
  }
}
