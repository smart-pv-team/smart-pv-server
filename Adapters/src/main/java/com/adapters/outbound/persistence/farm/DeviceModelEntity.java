package com.adapters.outbound.persistence.farm;

import com.domain.model.farm.DeviceModel;

public enum DeviceModelEntity {
  SUPLA_ROW01,
  SUPLA_MEW01;


  public static DeviceModel toDomain(DeviceModelEntity deviceModelEntity) {
    return DeviceModel.valueOf(deviceModelEntity.toString());
  }

  public static DeviceModelEntity fromDomain(DeviceModel deviceModel) {
    return DeviceModelEntity.valueOf(deviceModel.toString());
  }
}
