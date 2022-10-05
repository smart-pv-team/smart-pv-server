package smartpv.consumption;

import java.util.Date;
import smartpv.server.utils.DateTimeUtils;

public record ControlParameters(Integer priority, Float powerConsumption, Integer minHysteresis,
                                Integer maxHysteresis, Lock lock) {

  public ControlParameters withLock(Boolean status) {
    return new ControlParameters(priority, powerConsumption, minHysteresis, maxHysteresis,
        new Lock(status, DateTimeUtils.endOfDay()));
  }

  public record Lock(Boolean isLocked, Date date) {

  }
}
