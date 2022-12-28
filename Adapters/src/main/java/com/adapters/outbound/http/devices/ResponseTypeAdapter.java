package com.adapters.outbound.http.devices;

import com.adapters.outbound.http.devices.supla.mew01.SuplaMeterMew01ReadResponse;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01OnOffResponse;
import com.adapters.outbound.http.devices.supla.row01.SuplaConsumptionRow01ReadResponse;
import com.domain.model.management.farm.ResponseType;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

public enum ResponseTypeAdapter {
  SUPLA_METER_READ_MEW01,
  SUPLA_CONSUMPTION_ONOFF_ROW01,
  SUPLA_CONSUMPTION_READ_ROW01,
  NO_RESPONSE;

  public static Class<?> stringToResponseClass(ResponseTypeAdapter responseType) {
    return switch (responseType) {
      case SUPLA_METER_READ_MEW01 -> SuplaMeterMew01ReadResponse.class;
      case SUPLA_CONSUMPTION_ONOFF_ROW01 -> SuplaConsumptionRow01OnOffResponse.class;
      case SUPLA_CONSUMPTION_READ_ROW01 -> SuplaConsumptionRow01ReadResponse.class;
      case NO_RESPONSE -> Empty.class;
      default -> throw new IllegalStateException("Unexpected value: " + responseType);
    };
  }

  public static ResponseType toDomain(ResponseTypeAdapter responseType) {
    return ResponseType.valueOf(responseType.toString());
  }

  public static ResponseTypeAdapter fromDomain(ResponseType deviceType) {
    return ResponseTypeAdapter.valueOf(deviceType.toString());
  }

}
