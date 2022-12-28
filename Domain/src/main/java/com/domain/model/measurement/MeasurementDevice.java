package com.domain.model.measurement;

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
public class MeasurementDevice extends Device {

  private Long measuredEnergy;

  @Builder
  public MeasurementDevice(String id, String farmId, String name, String ipAddress,
      List<HttpEndpointData> endpoints, Long measuredEnergy, Date creationDate, DeviceModel deviceModel, Boolean isOn) {
    super(id, farmId, name, ipAddress, endpoints, creationDate, deviceModel, isOn);
    this.measuredEnergy = measuredEnergy;
  }

  public MeasurementDevice withMeasuredEnergy(Long measuredEnergy) {
    return new MeasurementDevice(getId(), getFarmId(), getName(), getIpAddress(), getEndpoints(), measuredEnergy,
        getCreationDate(), getDeviceModel(), getIsOn());
  }
}
