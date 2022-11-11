package com.domain.model.actions;

import com.domain.model.consumption.ConsumptionDevice;

public record ConsumptionReadAction(Boolean isOn, Action currentAction, ConsumptionDevice consumptionDevice) {

}
