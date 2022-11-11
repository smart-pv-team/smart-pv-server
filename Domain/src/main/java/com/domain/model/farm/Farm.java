package com.domain.model.farm;

import lombok.Builder;
import nonapi.io.github.classgraph.json.Id;

@Builder
public record Farm(@Id String id, String name, String description, AlgorithmType algorithmType,
                   Float energyLimit, Integer minutesBetweenDeviceStatusSwitch,
                   Integer minutesToAverageMeasurement) {

}
