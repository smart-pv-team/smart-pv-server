package com.domain.model.actions;

import com.domain.model.consumption.ConsumptionDevice;

public record ConsumptionOnOffAction(Boolean success, ConsumptionDevice consumptionDevice) {

}
