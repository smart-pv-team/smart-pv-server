package com.adapters.outbound.http.devices.supla.row01;

import com.adapters.outbound.http.devices.ConsumptionRead;
import com.domain.model.actions.Action;
import com.domain.model.actions.ConsumptionReadAction;
import com.domain.model.consumption.ConsumptionDevice;

public record SuplaConsumptionRow01ReadResponse(Boolean connected, Boolean on,
                                                Boolean currentOverload) implements ConsumptionRead {

  @Override
  public ConsumptionReadAction toDomain(ConsumptionDevice consumptionDevice) {
    return new ConsumptionReadAction(connected && on, Action.TURN_ON, consumptionDevice);
  }

}