package measurement;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import management.device.parsers.supla.PhaseResponse;
import measurement.persistence.record.MeasurementEntity;

public record MeasurementResponseMapper(boolean connected, Float support, Currency currency,
                                        Float pricePerUnit, Float totalCost,
                                        List<PhaseResponse> phases, String deviceId) {


  public MeasurementEntity toEntity() {
    float measurement = (float) phases.stream().mapToDouble(PhaseResponse::powerActive).sum();
    float reversedMeasurement = -measurement;
    return new MeasurementEntity(deviceId, reversedMeasurement, new Date());
  }
}