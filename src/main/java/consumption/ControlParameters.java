package consumption;

public record ControlParameters(Integer priority, Float powerConsumption, Integer minHysteresis,
                                Integer maxHysteresis, Boolean isLocked) {

}
