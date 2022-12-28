package com.domain.model.consumption;

import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.DeviceModel;
import com.domain.model.management.farm.HttpEndpointData;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumptionDevice extends Device {

  private ControlParameters controlParameters;
  private Long workingHours;

  @Builder
  public ConsumptionDevice(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints, Boolean isOn, ControlParameters controlParameters, Long workingHours,
      Date creationDate, DeviceModel deviceModel) {
    super(id, farmId, name, ipAddress, endpoints, creationDate, deviceModel, isOn);
    this.controlParameters = controlParameters;
    this.workingHours = workingHours;
  }

  public ConsumptionDevice withWorkingHours(Long workingHours) {
    return new ConsumptionDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), getIsOn(),
        getControlParameters(), workingHours, getCreationDate(), getDeviceModel());
  }

  public ConsumptionDevice withIsOn(Boolean isOn) {
    return new ConsumptionDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), isOn,
        getControlParameters(), getWorkingHours(), getCreationDate(), getDeviceModel());
  }

  public ConsumptionDevice withControlParameters(ControlParameters controlParameters) {
    return new ConsumptionDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), getIsOn(),
        controlParameters, getWorkingHours(), getCreationDate(), getDeviceModel());
  }

  public ConsumptionDevice withCreationDate(Date date) {
    return new ConsumptionDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), getIsOn(),
        getControlParameters(), getWorkingHours(), date, getDeviceModel());
  }
}

