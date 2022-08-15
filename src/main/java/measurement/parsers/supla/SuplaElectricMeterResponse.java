package measurement.parsers.supla;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import measurement.parsers.Response;
import measurement.persistence.record.MeasurementEntity;

public record SuplaElectricMeterResponse(boolean connected, Float support, Currency currency,
                                         Float pricePerUnit, Float totalCost,
                                         List<PhaseResponse> phases) implements Response {

  @Override
  public MeasurementEntity toMeasurementEntity(String id) {
    float measurement = (float) phases.stream().mapToDouble(PhaseResponse::powerActive).sum();
    float reversedMeasurement = -measurement;
    return new MeasurementEntity(id, reversedMeasurement, new Date());
  }
}
