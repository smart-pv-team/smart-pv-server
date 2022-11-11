package com.adapters.inbound.http.consumption;

import com.domain.model.consumption.ConsumptionDevice;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ConsumptionIsOnDto(boolean isOn) {

  public ConsumptionIsOnDto(@JsonProperty("isOn") boolean isOn) {
    this.isOn = isOn;
  }

  public static ConsumptionIsOnDto ofConsumerDeviceEntity(ConsumptionDevice consumptionDeviceEntity) {
    return new ConsumptionIsOnDto(consumptionDeviceEntity.getIsOn());
  }
}
