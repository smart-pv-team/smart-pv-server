package com.adapters.outbound.persistence.consumption;

import com.domain.model.consumption.ControlParameters;
import com.domain.model.consumption.ControlParameters.Lock;
import java.util.Date;
import lombok.Builder;

@Builder
public record ControlParametersEntity(Integer priority, Float powerConsumption, LockEntity lock, Date lastStatusChange,
                                      String lastStatus) {

  public static ControlParametersEntity fromDomain(ControlParameters controlParameters) {
    return builder()
        .lastStatusChange(controlParameters.lastStatusChange())
        .lastStatus(controlParameters.lastStatus())
        .lock(LockEntity
            .builder()
            .isLocked(controlParameters.lock().isLocked())
            .date(controlParameters.lock().date())
            .build())
        .powerConsumption(controlParameters.powerConsumption())
        .priority(controlParameters.priority())
        .build();
  }

  public static ControlParameters toDomain(ControlParametersEntity controlParameters) {
    return ControlParameters.builder()
        .lastStatusChange(controlParameters.lastStatusChange())
        .lastStatus(controlParameters.lastStatus())
        .lock(Lock
            .builder()
            .isLocked(controlParameters.lock().isLocked())
            .date(controlParameters.lock().date())
            .build())
        .powerConsumption(controlParameters.powerConsumption())
        .priority(controlParameters.priority())
        .build();
  }

  public ControlParametersEntity withPriority(Integer priority) {
    return new ControlParametersEntity(priority, powerConsumption, lock, lastStatusChange, lastStatus);
  }

  public ControlParametersEntity withPowerConsumption(Float powerConsumption) {
    return new ControlParametersEntity(priority, powerConsumption, lock, lastStatusChange, lastStatus);
  }

  @Builder
  public record LockEntity(Boolean isLocked, Date date) {

  }
}
