package com.domain.model.consumption;

import com.domain.model.farm.Device;
import com.domain.model.farm.HttpEndpointData;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumptionDevice extends Device {

  private Boolean isOn;
  private ControlParameters controlParameters;
  private Long workingHours;

  @Builder

  public ConsumptionDevice(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints, Boolean isOn, ControlParameters controlParameters, Long workingHours,
      Date creationDate) {
    super(id, farmId, name, ipAddress, endpoints, creationDate);
    this.isOn = isOn;
    this.controlParameters = controlParameters;
    this.workingHours = workingHours;
  }

  public ConsumptionDevice withWorkingHours(Long workingHours) {
    return new ConsumptionDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), getIsOn(),
        getControlParameters(), workingHours, getCreationDate());
  }
}

