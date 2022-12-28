package com.adapters.outbound.persistence.consumption;

import com.adapters.outbound.persistence.management.farm.DeviceEntity;
import com.adapters.outbound.persistence.management.farm.DeviceModelEntity;
import com.adapters.outbound.persistence.management.farm.HttpEndpointDataEntity;
import com.domain.model.consumption.ConsumptionDevice;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "consumptionDeviceEntity")
public class ConsumptionDeviceDocument extends DeviceEntity {

  private ControlParametersEntity controlParameters;
  private Long workingHours;

  @Builder
  public ConsumptionDeviceDocument(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointDataEntity> endpoints, Boolean isOn, ControlParametersEntity controlParameters,
      Long workingHours, Date creationDate, DeviceModelEntity deviceModel) {
    super(id, farmId, name, ipAddress, endpoints, creationDate, deviceModel, isOn);
    this.controlParameters = controlParameters;
    this.workingHours = workingHours;
  }

  public static ConsumptionDeviceDocument fromDomain(ConsumptionDevice consumptionDevice) {
    if (consumptionDevice == null) {
      return null;
    }
    return builder()
        .isOn(consumptionDevice.getIsOn())
        .farmId(consumptionDevice.getFarmId())
        .controlParameters(ControlParametersEntity.fromDomain(consumptionDevice.getControlParameters()))
        .workingHours(consumptionDevice.getWorkingHours())
        .endpoints(consumptionDevice.getEndpoints().stream().map(HttpEndpointDataEntity::fromDomain)
            .collect(Collectors.toList()))
        .ipAddress(consumptionDevice.getIpAddress())
        .name(consumptionDevice.getName())
        .id(consumptionDevice.getId())
        .creationDate(consumptionDevice.getCreationDate())
        .deviceModel(DeviceModelEntity.fromDomain(consumptionDevice.getDeviceModel()))
        .build();
  }

  public static ConsumptionDevice toDomain(ConsumptionDeviceDocument consumptionDevice) {
    if (consumptionDevice == null) {
      return null;
    }
    return ConsumptionDevice
        .builder()
        .isOn(consumptionDevice.getIsOn())
        .farmId(consumptionDevice.getFarmId())
        .controlParameters(ControlParametersEntity.toDomain(consumptionDevice.getControlParameters()))
        .workingHours(consumptionDevice.getWorkingHours())
        .endpoints(consumptionDevice.getEndpoints().stream().map(HttpEndpointDataEntity::toDomain)
            .collect(Collectors.toList()))
        .ipAddress(consumptionDevice.getIpAddress())
        .id(consumptionDevice.getId())
        .name(consumptionDevice.getName())
        .creationDate(consumptionDevice.getCreationDate())
        .deviceModel(DeviceModelEntity.toDomain(consumptionDevice.getDeviceModel()))
        .build();
  }
}

