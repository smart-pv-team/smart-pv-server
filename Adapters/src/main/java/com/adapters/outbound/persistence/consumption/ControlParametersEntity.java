package com.adapters.outbound.persistence.consumption;

import com.domain.model.consumption.ControlParameters;
import com.domain.model.consumption.ControlParameters.Lock;
import java.util.Date;
import lombok.Builder;

@Builder
public record ControlParametersEntity(Integer priority, Float powerConsumption, Integer minHysteresis,
                                      Integer maxHysteresis, LockEntity lock, Date lastStatusChange) {

  public static ControlParametersEntity fromDomain(ControlParameters controlParameters) {
    return builder()
        .lastStatusChange(controlParameters.lastStatusChange())
        .lock(LockEntity
            .builder()
            .isLocked(controlParameters.lock().isLocked())
            .date(controlParameters.lock().date())
            .build())
        .maxHysteresis(controlParameters.maxHysteresis())
        .minHysteresis(controlParameters.minHysteresis())
        .powerConsumption(controlParameters.powerConsumption())
        .priority(controlParameters.priority())
        .build();
  }

  public static ControlParameters toDomain(ControlParametersEntity controlParameters) {
    return ControlParameters.builder()
        .lastStatusChange(controlParameters.lastStatusChange())
        .lock(Lock
            .builder()
            .isLocked(controlParameters.lock().isLocked())
            .date(controlParameters.lock().date())
            .build())
        .maxHysteresis(controlParameters.maxHysteresis())
        .minHysteresis(controlParameters.minHysteresis())
        .powerConsumption(controlParameters.powerConsumption())
        .priority(controlParameters.priority())
        .build();
  }

  @Builder
  public record LockEntity(Boolean isLocked, Date date) {

  }
}
