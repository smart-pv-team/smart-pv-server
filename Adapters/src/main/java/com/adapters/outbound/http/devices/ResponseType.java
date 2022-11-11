package com.adapters.outbound.http.devices;

import com.adapters.outbound.http.devices.supla.mew01.SuplaMeterMew01Response;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01OnOffResponse;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01ReadResponse;

public enum ResponseType {
  /*  SUPLA_METER_MEW01,
    SUPLA_CONSUMPTION_ONOFF_ROW01,
    SUPLA_CONSUMPTION_READ_ROW01,*/
  SUPLA_STATUS,
  SUPLA_ELECTRIC_METER,

  SUPLA_SWITCH;


  public static Class<?> stringToResponseClass(ResponseType responseType) {
    return switch (responseType) {
      case SUPLA_ELECTRIC_METER -> SuplaMeterMew01Response.class;
      case SUPLA_SWITCH -> SuplaConsumptionRow01OnOffResponse.class;
      case SUPLA_STATUS -> SuplaConsumptionRow01ReadResponse.class;
      default -> throw new IllegalStateException("Unexpected value: " + responseType);
    };
  }
}
