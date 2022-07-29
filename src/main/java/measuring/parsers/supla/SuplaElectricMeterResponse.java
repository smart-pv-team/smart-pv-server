package measuring.parsers.supla;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import measuring.MeasurementEntity;
import measuring.parsers.Response;

public record SuplaElectricMeterResponse(boolean connected, Float support, Currency currency,
                                         Float pricePerUnit, Float totalCost,
                                         List<PhaseResponse> phases) implements Response {

  @Override
  public MeasurementEntity toMeasurementEntity(String id) {
    float measurement = (float) phases.stream().mapToDouble(PhaseResponse::powerActive).sum();
    return new MeasurementEntity(id, measurement, new Date());
  }
}
