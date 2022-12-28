package com.adapters.outbound.persistence.measurement;

import com.adapters.outbound.persistence.management.farm.DeviceEntity;
import com.adapters.outbound.persistence.management.farm.DeviceModelEntity;
import com.adapters.outbound.persistence.management.farm.HttpEndpointDataEntity;
import com.domain.model.measurement.MeasurementDevice;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "measurementDeviceEntity")
@Getter
public class MeasurementDeviceDocument extends DeviceEntity {

  private final Long measuredEnergy;

  @Builder
  public MeasurementDeviceDocument(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointDataEntity> endpoints, Long measuredEnergy, Date creationDate, DeviceModelEntity deviceModel,
      Boolean isOn) {
    super(id, farmId, name, ipAddress, endpoints, creationDate, deviceModel, isOn);
    this.measuredEnergy = measuredEnergy;
  }

  public static MeasurementDeviceDocument fromDomain(MeasurementDevice measurementDevice) {
    return builder()
        .endpoints(measurementDevice.getEndpoints().stream().map(HttpEndpointDataEntity::fromDomain)
            .collect(Collectors.toList()))
        .id(measurementDevice.getId())
        .farmId(measurementDevice.getFarmId())
        .measuredEnergy(measurementDevice.getMeasuredEnergy())
        .name(measurementDevice.getName())
        .ipAddress(measurementDevice.getIpAddress())
        .creationDate(measurementDevice.getCreationDate())
        .deviceModel(DeviceModelEntity.fromDomain(measurementDevice.getDeviceModel()))
        .isOn(measurementDevice.getIsOn())
        .build();
  }

  public static MeasurementDevice toDomain(MeasurementDeviceDocument measurementDevice) {
    return MeasurementDevice.builder()
        .endpoints(measurementDevice.getEndpoints().stream().map(HttpEndpointDataEntity::toDomain)
            .collect(Collectors.toList()))
        .id(measurementDevice.getId())
        .farmId(measurementDevice.getFarmId())
        .measuredEnergy(measurementDevice.getMeasuredEnergy())
        .name(measurementDevice.getName())
        .ipAddress(measurementDevice.getIpAddress())
        .creationDate(measurementDevice.getCreationDate())
        .deviceModel(DeviceModelEntity.toDomain(measurementDevice.getDeviceModel()))
        .isOn(measurementDevice.getIsOn())
        .build();
  }

}
