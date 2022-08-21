package consumption;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlParametersMapper {

  public int priority;
  public float powerConsumption;
  public int minHysteresis;
  public int maxHysteresis;
  public boolean isLocked;

  public ControlParametersMapper(@JsonProperty("priority") int priority,
      @JsonProperty("powerConsumption") float powerConsumption,
      @JsonProperty("minHysteresis") int minHysteresis,
      @JsonProperty("maxHysteresis") int maxHysteresis,
      @JsonProperty("isLocked") boolean isLocked) {
    this.priority = priority;
    this.powerConsumption = powerConsumption;
    this.minHysteresis = minHysteresis;
    this.maxHysteresis = maxHysteresis;
    this.isLocked = isLocked;
  }

  public static ControlParameters toControlParameters(
      ControlParametersMapper controlParametersMapper) {
    return new ControlParameters(controlParametersMapper.priority,
        controlParametersMapper.powerConsumption, controlParametersMapper.minHysteresis,
        controlParametersMapper.maxHysteresis, controlParametersMapper.isLocked);
  }

  public static ControlParametersMapper ofControlParameters(ControlParameters controlParameters) {
    return new ControlParametersMapper(controlParameters.getPriority(),
        controlParameters.getPowerConsumption(),
        controlParameters.getMinHysteresis(), controlParameters.getMaxHysteresis(),
        controlParameters.isLocked());
  }
}

