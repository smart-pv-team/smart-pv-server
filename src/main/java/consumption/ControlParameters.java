package consumption;

import java.util.Date;

public record ControlParameters(Integer priority, Float powerConsumption, Integer minHysteresis,
                                Integer maxHysteresis, Lock lock) {

  public ControlParameters withLock(Boolean status, Date date) {
    return new ControlParameters(priority, powerConsumption, minHysteresis, maxHysteresis,
        new Lock(status, date));
  }

  public record Lock(Boolean isLocked, Date date) {

  }
}
