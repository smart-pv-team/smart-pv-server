package management.device.parsers;

import management.device.parsers.supla.SuplaDeviceStatusResponse;
import management.device.parsers.supla.SuplaElectricMeterResponse;
import org.springframework.stereotype.Component;

@Component
public class StringToResponseClassParser {

  public Class<?> stringToResponseClass(ResponseType responseType) {
    return switch (responseType) {
      case SUPLA_ELECTRIC_METER -> SuplaElectricMeterResponse.class;
      case SUPLA_SWITCH -> SuplaDeviceStatusResponse.class;
      default -> throw new IllegalStateException("Unexpected value: " + responseType);
    };
  }

}
