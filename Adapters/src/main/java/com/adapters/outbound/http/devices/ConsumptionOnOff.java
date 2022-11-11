package com.adapters.outbound.http.devices;

import com.domain.model.actions.ConsumptionOnOffAction;
import com.domain.model.consumption.ConsumptionDevice;

public interface ConsumptionOnOff {

  ConsumptionOnOffAction toDomain(ConsumptionDevice consumptionDevice);
}
