package com.adapters.outbound.http.devices;

import com.adapters.outbound.http.devices.supla.mew01.SuplaMeterMew01ReadResponse;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01OnOffResponse;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01ReadResponse;

public enum ResponseType {
  SUPLA_METER_READ_MEW01,
  SUPLA_CONSUMPTION_ONOFF_ROW01,
  SUPLA_CONSUMPTION_READ_ROW01;

  public static Class<?> stringToResponseClass(ResponseType responseType) {
    return switch (responseType) {
      case SUPLA_METER_READ_MEW01 -> SuplaMeterMew01ReadResponse.class;
      case SUPLA_CONSUMPTION_ONOFF_ROW01 -> SuplaConsumptionRow01OnOffResponse.class;
      case SUPLA_CONSUMPTION_READ_ROW01 -> SuplaConsumptionRow01ReadResponse.class;
      default -> throw new IllegalStateException("Unexpected value: " + responseType);
    };
  }
}
