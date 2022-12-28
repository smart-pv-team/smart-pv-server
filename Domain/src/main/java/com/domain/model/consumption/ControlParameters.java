package com.domain.model.consumption;

import java.util.Date;
import lombok.Builder;

@Builder
public record ControlParameters(Integer priority, Float powerConsumption, Lock lock, Date lastStatusChange,
                                String lastStatus) {

  public ControlParameters withLock(Boolean status, Date date) {
    return new ControlParameters(priority, powerConsumption, new Lock(status, date), lastStatusChange, lastStatus);
  }

  public ControlParameters withLastStatusChange(Date lastStatusChange) {
    return new ControlParameters(priority, powerConsumption, lock, lastStatusChange, lastStatus);
  }

  public ControlParameters withStatusChange(String lastStatus) {
    return new ControlParameters(priority, powerConsumption, lock, lastStatusChange, lastStatus);
  }

  @Builder
  public record Lock(Boolean isLocked, Date date) {

  }
}
