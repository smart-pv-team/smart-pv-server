package com.domain.model.consumption;

import java.util.Date;
import lombok.Builder;

@Builder
public record ControlParameters(Integer priority, Float powerConsumption, Integer minHysteresis,
                                Integer maxHysteresis, Lock lock, Date lastStatusChange) {

  public ControlParameters withLock(Boolean status, Date date) {
    return new ControlParameters(priority, powerConsumption, minHysteresis, maxHysteresis,
        new Lock(status, date), lastStatusChange);
  }

  public ControlParameters withLastStatusChange(Date lastStatusChange) {
    return new ControlParameters(priority, powerConsumption, minHysteresis, maxHysteresis, lock, lastStatusChange);
  }

  @Builder
  public record Lock(Boolean isLocked, Date date) {

  }
}
