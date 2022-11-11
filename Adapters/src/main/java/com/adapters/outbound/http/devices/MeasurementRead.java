package com.adapters.outbound.http.devices;

import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.measurement.MeasurementDevice;

public interface MeasurementRead extends Response {

  MeasurementReadAction toDomain(MeasurementDevice measurementDevice);
}
