package com.adapters.outbound.persistence.management.farm;

import com.domain.model.management.farm.AlgorithmType;
import com.domain.model.management.farm.Farm;
import lombok.Builder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "farmEntity")
public record FarmDocument(@Id String id, String name, String description, AlgorithmType algorithmType,
                           Float energyLimit, Integer minutesBetweenDeviceStatusSwitch,
                           Integer minutesToAverageMeasurement, Boolean running) {

  public static FarmDocument fromDomain(Farm farm) {
    return builder()
        .algorithmType(farm.algorithmType())
        .id(farm.id())
        .description(farm.description())
        .minutesToAverageMeasurement(farm.minutesToAverageMeasurement())
        .minutesBetweenDeviceStatusSwitch(farm.minutesBetweenDeviceStatusSwitch())
        .energyLimit(farm.energyLimit())
        .name(farm.name())
        .running(farm.running())
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
        .name(farm.name())
        .running(farm.running())
        .build();
  }

  public FarmDocument withAlgorithmType(AlgorithmType algorithmType) {
    return new FarmDocument(id, name, description, algorithmType, energyLimit, minutesBetweenDeviceStatusSwitch,
        minutesToAverageMeasurement, running);
  }

  public FarmDocument withRunning(Boolean running) {
    return new FarmDocument(id, name, description, algorithmType, energyLimit, minutesBetweenDeviceStatusSwitch,
        minutesToAverageMeasurement, running);
  }

}
