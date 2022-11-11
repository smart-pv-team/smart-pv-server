package com.adapters.outbound.http.devices.supla.row01;

import com.adapters.outbound.http.devices.ConsumptionOnOff;
import com.domain.model.actions.ConsumptionOnOffAction;
import com.domain.model.consumption.ConsumptionDevice;

public record SuplaConsumptionRow01OnOffResponse(Boolean success) implements ConsumptionOnOff {


  @Override
  public ConsumptionOnOffAction toDomain(ConsumptionDevice consumptionDevice) {
    return new ConsumptionOnOffAction(success, consumptionDevice);
  }
}
