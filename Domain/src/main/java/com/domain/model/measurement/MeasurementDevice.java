package com.domain.model.measurement;

import com.domain.model.farm.Device;
import com.domain.model.farm.HttpEndpointData;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MeasurementDevice extends Device {

  private final Long measuredEnergy;

  @Builder

  public MeasurementDevice(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints, Long measuredEnergy) {
    super(id, farmId, name, ipAddress, endpoints);
    this.measuredEnergy = measuredEnergy;
  }

  public MeasurementDevice withMeasuredEnergy(Long measuredEnergy) {
    return new MeasurementDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), measuredEnergy);
  }
}
