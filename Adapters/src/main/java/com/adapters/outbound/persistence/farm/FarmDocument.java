package com.adapters.outbound.persistence.farm;

import com.domain.model.farm.AlgorithmType;
import com.domain.model.farm.Farm;
import lombok.Builder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "farmEntity")
public record FarmDocument(@Id String id, String name, String description, AlgorithmType algorithmType,
                           Float energyLimit, Integer minutesBetweenDeviceStatusSwitch,
                           Integer minutesToAverageMeasurement) {

  public static FarmDocument fromDomain(Farm farm) {
    return builder()
        .algorithmType(farm.algorithmType())
        .id(farm.id())
        .description(farm.description())
        .minutesToAverageMeasurement(farm.minutesToAverageMeasurement())
        .minutesBetweenDeviceStatusSwitch(farm.minutesBetweenDeviceStatusSwitch())
        .energyLimit(farm.energyLimit())
        .build();
  }

  public static Farm toDomain(FarmDocument farm) {
    return Farm.builder()
        .algorithmType(farm.algorithmType())
        .id(farm.id())
        .description(farm.description())
        .minutesToAverageMeasurement(farm.minutesToAverageMeasurement())
        .minutesBetweenDeviceStatusSwitch(farm.minutesBetweenDeviceStatusSwitch())
        .energyLimit(farm.energyLimit())
        .build();
  }
}
