package com.adapters.outbound.http.devices.supla.mew01;

import com.adapters.outbound.http.devices.MeasurementRead;
import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.measurement.MeasurementDevice;
import java.util.Currency;
import java.util.List;

public record SuplaMeterMew01Response(boolean connected, Float support, Currency currency,
                                      Float pricePerUnit, Float totalCost,
                                      List<PhaseResponse> phases) implements MeasurementRead {

  @Override
  public MeasurementReadAction toDomain(MeasurementDevice measurementDevice) {
    Float measuredValue = (float) -phases.stream().mapToDouble(PhaseResponse::powerActive).sum();
    return new MeasurementReadAction(measuredValue, measurementDevice);
  }

  public record PhaseResponse(Integer number, Integer frequency, Float voltage, Float current,
                              Float powerActive, Float powerReactive, Float powerApparent,
                              Float powerFactor, Float phaseAngle, Float totalForwardActiveEnergy,
                              Float totalReverseActiveEnergy, Float totalForwardReactiveEnergy,
                              Float totalReverseReactiveEnergy) {

  }
}
