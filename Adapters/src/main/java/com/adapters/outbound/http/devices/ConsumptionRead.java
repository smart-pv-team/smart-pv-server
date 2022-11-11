package com.adapters.outbound.http.devices;

import com.domain.model.actions.ConsumptionReadAction;
import com.domain.model.consumption.ConsumptionDevice;

public interface ConsumptionRead extends Response {

  ConsumptionReadAction toDomain(ConsumptionDevice consumptionDevice);

}
