package measurement.parsers;

import measurement.parsers.supla.SuplaElectricMeterResponse;
import org.springframework.stereotype.Component;

@Component
public class StringToResponseClassParser {

  public Class<?> stringToResponseClass(ResponseType responseType) {
    return switch (responseType) {
      case SUPLA_ELECTRIC_METER -> SuplaElectricMeterResponse.class;
      default -> throw new IllegalStateException("Unexpected value: " + responseType);
    };
  }

}
