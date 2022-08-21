package consumption;

import lombok.Data;

@Data
public class ControlParameters {

  private int priority;
  private float powerConsumption;
  private int minHysteresis;
  private int maxHysteresis;
  private boolean isLocked;

  public ControlParameters(int priority, float powerConsumption, int minHysteresis,
      int maxHysteresis, boolean isLocked) {
    this.priority = priority;
    this.powerConsumption = powerConsumption;
    this.minHysteresis = minHysteresis;
    this.maxHysteresis = maxHysteresis;
    this.isLocked = isLocked;
  }
}
